#include <stdio.h>
#include <sys/ipc.h>
#include <unistd.h>
#include <stdlib.h>
#include <semaphore.h>
#include <sys/shm.h>
#include <sys/wait.h>

#define NUM_PROCESSES 5

int main() {

    int i, j, pid;

    int* turn;

    int shmid;
    int shmid2;
    sem_t* sem;

    sem_t* sem2;

    shmid = shmget(IPC_PRIVATE, sizeof(sem_t) * 2, IPC_CREAT | 0600);
    shmid2 = shmget(IPC_PRIVATE, sizeof(int), IPC_CREAT | 0600);

    turn = shmat(shmid2, NULL, 0);
    *turn = 0;

    sem = (sem_t*) shmat(shmid, NULL, 0);
    sem2 = sem + sizeof(sem_t);

    sem_init(sem, 1, 1);
    sem_init(sem2, 1, 1);

    for(i=0; i<NUM_PROCESSES; i++)
    {
        if((pid = fork()) == 0) {
            break;
        }
    }

    if(pid == 0) {
        sem_wait(sem);
        sem_wait(sem2);
        while (*turn != i) {
            sem_post(sem);
            sem_post(sem2);
            sem_wait(sem);
            sem_wait(sem2);
        }

        *turn = *turn + 1;

        printf("I am child %d\n", i);

        for(j = i*10; j<i*10 + 10; j++){
            printf("%d ", j);
            fflush(stdout);
            usleep(100000);
	    }

        printf("\n\n");
        sem_post(sem);
        sem_post(sem2);
    }
    else {
        for(i=0; i<NUM_PROCESSES; i++)
            wait(NULL);

        sem_destroy(sem);
        shmctl(shmid, IPC_RMID, 0);
    }
}
