/*
Demonstration of using multiple processes for parallel problem solving.
*/
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main() {
  int numInputs, userInput, childPid, childResult;
  // Since largest number is 10 digits, a 12 characters string is more
  // than enough

  scanf("%d", &numInputs);
  if (numInputs > 10) {
    printf("Number of inputs must be between 0 and 9!");
    return -1;
  }

  int inputs[10][2];
  for (int i = 0; i < numInputs; i++) {
    int inputVal;
    scanf("%d", &inputVal);

    childPid = fork();

    if (childPid == 0) {
      char cStringExample[12];
      sprintf(cStringExample, "%d", inputVal);
      execl("./PF", "PF", cStringExample, NULL);
    }
    else{
        inputs[i][0] = inputVal;
        inputs[i][1] = childPid;
    }
  }

  for (int i=0; i<numInputs; i++){
      int finishedPID = wait(&childResult);
      for (int j=0; j<numInputs; j++){
          if (inputs[j][1] == finishedPID){
              printf("%d has %d prime factors\n", inputs[j][0], WEXITSTATUS(childResult));
          }
      }
  }
}
