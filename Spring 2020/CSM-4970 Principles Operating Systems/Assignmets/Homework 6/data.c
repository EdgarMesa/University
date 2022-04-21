#include <stdio.h>
#include <stdlib.h>


int main(int argc,char*argv[])
{                 

	
	int* data = malloc(100 * sizeof(int));
	data[0] = 1;
	free(data);
	printf("%d\n",*data);

	exit(0);

}