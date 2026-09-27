#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>

int main() {

    printf("Be patient, the program will take around 7 seconds to run.\n");
    printf("At the end you can do \"cat results.out\" to see the result.\n");

    //
    // Add code here to pipe from ./slow 5 to ./talk and redirect
    // output of ./talk to results.out
    // I.e. your program should do the equivalent of ./slow 5 | talk > results.out
    // WITHOUT using | and > from the shell.
    // Look at how we did < and > and | in the previous parts of this lab, and do the same!

    int p[2];

    if (pipe(p) < 0) {
        perror("lab2p2f pipe error: ");
    }

    if (fork() != 0) { //Parent process: consumer (talk.c)
        close(p[1]);
        dup2(p[0], STDIN_FILENO);
        close(p[0]);

        int fp_out = open("./results.out", O_CREAT | O_WRONLY, 0644);
        dup2(fp_out, STDOUT_FILENO);
        close(fp_out);

        execl("./talk", "./talk", NULL);
        return 1;
    }

    else {
        close(p[0]);
        dup2(p[1], STDOUT_FILENO);
        close(p[1]);

        execl("./slow", "./slow", "5", NULL);
        exit(1);
    }

}
