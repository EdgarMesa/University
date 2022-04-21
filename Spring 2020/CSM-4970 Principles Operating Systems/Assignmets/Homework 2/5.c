#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <assert.h>



int main(int argc, char*argv[]) {

FILE * fd;
int i;

fd = fopen("/root/Documents/Garbage/file.txt","w");


int rc = fork();

if (rc < 0) {         // fork failed; exit1

	fprintf(stderr, "fork failed\n");
	exit(1);

} else if (rc == 0) { // child (new process)

	int wait_id = wait(NULL);

	printf("hello, I am child (pid:%d)  wait id: %d\n ", (int) getpid(),wait_id);
	char*myargs[3];
	myargs[0] = "/bin/ls"; 
	myargs[1] = NULL;           // marks end of array
	execvp(myargs[0], myargs);  // runs word count






	} else {              // parent goes down this path (main)

	//usleep(10 * 1000);	
	int wait_id = wait(NULL);
	printf("hello, I am parent of %d (pid:%d)  wait id: %d\n",
		rc, (int) getpid(),wait_id);

	
	}

	return 0;
}