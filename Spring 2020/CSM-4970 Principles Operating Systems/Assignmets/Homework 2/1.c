#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>


int main(int argc, char*argv[]) {

int x = 100;

int rc = fork();

if (rc < 0) {         // fork failed; exit1

	fprintf(stderr, "fork failed\n");
	exit(1);

} else if (rc == 0) { // child (new process)

	x = x +10;
	printf("hello, I am child (pid:%d)\nx is equal to: %d\n", (int) getpid(),x);

	} else {              // parent goes down this path (main)

	x = x +30;
	printf("hello, I am parent of %d (pid:%d)\nx is equal to: %d\n",
		rc, (int) getpid(),x);
	
	}

	return 0;
}