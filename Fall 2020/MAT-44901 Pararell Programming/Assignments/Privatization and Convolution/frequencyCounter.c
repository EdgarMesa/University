// single threaded program to count the char, digram and trigram frequency of a 
// given file text.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>


FILE* readFile(char* filename, char* mode);
void frequencyCounter(char *array, int *frequency, int *digram , int *trigram, long size);
int IDX(int row, int col, int LDA);
void nGramFrequency(char *array, int *n_gram, long size, int n);
void printTrigram(int *array, long size);
void printFrequency(int* array, int size);
void printDigram(int* array, int size);
void selectionSort(int *array, int *indexes, int size); 
void swap(int *x, int *y);

FILE* aFile;
char *array;
int frequency[26];
int digram [26 * 26];
int trigram [26 * 26 * 26];
time_t start,end;



int main(int argc, char *argv[])
{

	
    if (argc != 2)
    {
    	printf("Error! please enter the correct number of command arguments\n");
    	exit(1);
    }

    char* a = argv[1];

    aFile = readFile(a, "r");
    long size;

    fseek( aFile , 0L , SEEK_END);
    size = ftell(aFile);
    rewind(aFile);


    // allocate space for the array.
    array = malloc((size + 1) * sizeof(*array)); // size + 1 byte for the \0 .

    // reads the file into the array. Adding the \0 at the end of the array.
    fread(array, size, 1, aFile);

    array[size] = '\0';
    


	start=clock();


	frequencyCounter(array, frequency, digram, trigram, size);
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);


	free(array);


}

// read the files and returns a File pointer
FILE* readFile(char* filename, char* mode)
{
	FILE* file;

	if ((file = fopen(filename, mode)) == NULL){
		printf("Error! opening file");
		exit(1);
	}

	return file;
}


void frequencyCounter(char *array, int *frequency, int *digram , int *trigram, long size)
{

	for(int i = 0; i < size; i++)
	{	
		char c = array[i];
		if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
		{
			c = tolower(c);
			
			frequency[(c - 'a')] += 1;
		}
		

		
	}
	// count digram and trigram frequency.
	nGramFrequency(array, digram, size, 2);
	nGramFrequency(array, trigram, size, 3);
	end=clock();

	// print all the information obtained
	printf("\nChar Frequency:\n");
	printf("---------------\n");
	printFrequency(frequency, 26);

	printf("\nDigram Frequency:\n");
	printf("-----------------\n");
	printDigram(digram, 26 * 26);

	printf("\nTrigram Frequency:\n");
	printf("------------------\n");
	printTrigram(trigram, 26 * 26 * 26);


}

// calculate n-grams of a given array.
void nGramFrequency(char *array, int *n_gram, long size, int n)
{
	for(int i = 0; i < size-n; i++)
	{	
		int index = 0;
		for(int j = 0; j < n; j++) 
		{
			char c = array[i + j];
			
			if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) // just alphabetic characters.
			{	
							
				c = tolower(c);
				index = index * 26 + (c -'a');
					
			}
			else{goto OUTER;}


		}

		n_gram[index] += 1; // increase counter.
		OUTER:continue;
		
		
	}
}

void printTrigram(int *array, long size)
{
	for (int i = 0; i < size; i++)
	{	
		int count = array[i];
		if (count > 0)
		{
			printf("%c%c%c --> %d\n", (i/676 + 'a'), ((i/26)%26 + 'a'), (i%26 + 'a'), array[i]);
		}

	}

	int *indexes = malloc(sizeof(int) * size);
	for (int i = 0; i < size; i++)
	{
		indexes[i] = i;
	}

	selectionSort(array, indexes, size);

	printf("\nTrigram ascending order:\n");
	printf("------------------------\n");
	for (int i = 0; i < size; i++)
	{	
		int count = array[i];
		int index = indexes[i];
		if (count > 0)
		{
			printf("%c%c%c --> %d\n", (index/676 + 'a'), ((index/26)%26 + 'a'), (index%26 + 'a'), array[i]);
		}

	}

	free(indexes);	
}


void printFrequency(int *array, int size)
{	

	
    for (int i = 0; i < size; i++)
  	{
		  printf("%c --> %d\n", (i + 'a') , array[i]);
	}

	int *indexes = malloc(sizeof(int) * size);
	for (int i = 0; i < size; i++)
	{
		indexes[i] = i;
		
	}
	
	selectionSort(array, indexes, size);
	printf("\nChar ascending order:\n");
	printf("---------------------\n");

	for (int i = 0; i < size; i++)
  	{
		  printf("%c --> %d\n", (indexes[i] + 'a') , array[i]);
	}	  

	free(indexes);

}

void printDigram(int* array, int size)
{

	for (int i = 0; i < size; i++)
	{	
		int count = array[i];
		if (count > 0)
		{
			printf("%c%c --> %d\n", (i/26 + 'a'), (i%26 + 'a'), array[i]);
		}
	}

	int *indexes = malloc(sizeof(int) * size);
	for (int i = 0; i < size; i++)
	{
		indexes[i] = i;
	}

	
	selectionSort(array, indexes, size);
	printf("\nDigram ascending order:\n");
	printf("-----------------------\n");

	for (int i = 0; i < size; i++)
	{	
		int count = array[i];
		if (count > 0)
		{
			printf("%c%c --> %d\n", (indexes[i]/26 + 'a'), (indexes[i]%26 + 'a'), array[i]);
		}
	}

	free(indexes);
	


}


void swap(int *xp, int *yp) 
{ 
    int temp = *xp; 
    *xp = *yp; 
    *yp = temp; 
} 
  
// simple selection algorithm to order array and indexes.
void selectionSort(int *array, int *indexes, int size) 
{ 
    int i, j, min_idx; 
  
    // one by one move boundary of unsorted subarray 
    for (i = 0; i < size-1; i++) 
    { 
        // find the minimum element in unsorted array 
        min_idx = i; 
        for (j = i+1; j < size; j++) 
          if (array[j] > array[min_idx]) 
            min_idx = j; 
  
        // swap the found minimum element with the first element 
        swap(&array[min_idx], &array[i]); 
        swap(&indexes[min_idx], &indexes[i]); 
    } 
}

int IDX(int row, int col, int LDA){return row*LDA+col;}



