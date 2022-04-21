#include <stdio.h>
#include <stdlib.h>



int main(int argc,char*argv[])
{                
	int memory = 2 * sizeof(int);
	int* vector = malloc(memory) ;



	int index = 0;

	while(index < 10)
	{
		int value;
		int capacity = memory / 4;
		printf("Enter value for the index %d of the vector: ",index);
		scanf("%d",&value);



		if(capacity == index+1)
		{
			memory = 2 * memory;

			vector = realloc(vector,memory);
			printf("Increased capacity of the vector from %d to %d \n",capacity,capacity*2);


		}

		vector[index] = value;

		printf("Vector is %d out of %d full\n\n",index+1,memory/4);






		index++;

	}

}
