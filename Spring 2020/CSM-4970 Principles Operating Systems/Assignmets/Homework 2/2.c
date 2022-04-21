#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <assert.h>



int main(int argc, char*argv[]) {

FILE * fd;
int i;

fd = fopen("./file.txt","w");


int rc = fork();

if (rc < 0) {         // fork failed; exit1

	fprintf(stderr, "fork failed\n");
	exit(1);

} else if (rc == 0) { // child (new process)



	printf("hello, I am child (pid:%d)\n", (int) getpid());





	fprintf (fd, "hello world\n");
    fclose(fd);

	} else {              // parent goes down this path (main)

	printf("hello, I am parent of %d (pid:%d)\n",
		rc, (int) getpid());

	fprintf (fd, "hola mundo\n");
    fclose(fd);
	
	}

	return 0;
}