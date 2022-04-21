#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <assert.h>



int main(int argc, char*argv[]) {

FILE * fd;

fd = fopen("/root/Documents/Garbage/file.txt","w");


int rc = fork();

if (rc < 0) {         // fork failed; exit1

	fprintf(stderr, "fork failed\n");
	exit(1);

} else if (rc == 0) { // child (new process)




	printf("hello, I am child (pid:%d)\n", (int) getpid());
	close(STDOUT_FILENO);
	printf("hello, I am child (pid:%d)\n", (int) getpid());



	} else {              // parent goes down this path (main)

	
	int rc_wait = wait(NULL);
	printf("hello, I am parent of %d (rc_wait:%d) (pid:%d)\n",	rc, rc_wait, (int) getpid());

	//fprintf (fd, "hola mundo\n");
    //fclose(fd);
	
	}

	return 0;
}