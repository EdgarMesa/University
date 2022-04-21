
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <assert.h>

const int READ = 0, WRITE = 1;

int main(int argc, char*argv[]) {
int i;

int fd[2];
pipe(fd);

int pid2,pid = fork();


if(pid < 0){         // fork failed; exit1

	fprintf(stderr, "fork failed\n");
	exit(1);

} 

if(pid == 0) { // child (new process)

	close(fd[READ]);
	printf("hello, I am the first child (pid:%d)\n", (int) getpid());
	dup2(fd[WRITE],WRITE);

	execlp("ps","ps","-aux",NULL);





	} else{              // parent goes down this path (main)

	pid2 = fork();

	if(pid2 == 0)
	{
		close(fd[WRITE]);

		printf("hello, I am the second child (pid:%d)\n", (int) getpid());


		dup2(fd[READ],READ);

		execlp("grep","grep","ps",NULL);


	}

	
	waitpid(pid,&i,0);
	printf("hello, I am parent (pid:%d)\n", (int) getpid());

	close(fd[WRITE]);

	waitpid(pid2,&i,0);



	
	}

	return 0;
	
}