#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <assert.h>



int main(int argc, char*argv[]) {

FILE * fd;
int i;



int rc = fork();

if (rc < 0) {         // fork failed; exit1

	fprintf(stderr, "fork failed\n");
	exit(1);

} else if (rc == 0) { // child (new process)



	printf("hello (pid:%d)\n", (int) getpid());




	} else {              // parent goes down this path (main)

	usleep(10 * 1000);	
	printf("Goodbye (pid:%d)\n",
		rc, (int) getpid());


	
	}

	return 0;
}