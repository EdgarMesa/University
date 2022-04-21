#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <assert.h>
#include <time.h>
#include <unistd.h>




int
main(int argc, char*argv[])
{
	clock_t countdown;

	int memory = atoi(argv[1]);
	memory = memory * 1000000;
	double time = atof(argv[2]);
	;

	int *x = (int *) malloc(memory);
	int pid = getpid();
	printf("%d\n",pid );

	while(1){
	for(int i = 0; i < sizeof(x); ++i) 
	{
		int p = x[i];
		//printf("%d\n", i);
		//printf("%lf\n",(double) clock() / CLOCKS_PER_SEC);
		//printf("%lf\n",time);
		//printf("\n");
		if( (double) clock() / CLOCKS_PER_SEC >= time)
		{
			exit(0);
		}

	}
}

}