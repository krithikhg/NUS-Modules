#include <stdio.h>
#include <semaphore.h>
#include <sys/ipc.h>
#include <sys/shm.h>

int nproc;
int* count;
int shmid;
int shmid2;
sem_t* barrier;
sem_t* mtx;

void init_barrier(int numproc) {
    nproc = numproc;
    shmid = shmget(IPC_PRIVATE, sizeof(sem_t) * 2, IPC_CREAT | 0600);
    shmid2 = shmget(IPC_PRIVATE, sizeof(int), IPC_CREAT | 0600);

    barrier = shmat(shmid, NULL, 0);
    mtx = sizeof(sem_t) + barrier;

    count = shmat(shmid2, NULL, 0);
    *count = 0;

    sem_init(barrier, 1, 0);
    sem_init(mtx, 1, 1);
}

void reach_barrier() {
    sem_wait(mtx);
    *count = *count + 1;
    int temp = *count;
    sem_post(mtx);
    if (temp == nproc) {
        sem_post(barrier);
    }
    else {
        sem_wait(barrier);

        sem_post(barrier);
    }
}

void destroy_barrier(int my_pid) {
    if(my_pid != 0) {
        // Destroy the semaphores and detach
        // and free any shared memory. Notice
        // that we explicity check that it is
        // the parent doing it.
        sem_destroy(barrier);
        sem_destroy(mtx);

        shmctl(shmid, IPC_RMID, 0);
        shmctl(shmid2, IPC_RMID, 0);
    }
}
