#include <stdio.h>
#include <stdlib.h>
#include <time.h>


FILE* readFile(char* filename, char* mode);
void fileToArray(FILE* file, double* array, int size);
void arrayToFile(FILE* file, double* array, int size);
void sequentialScan(double *array, int size);
int IDX(int row, int col, int LDA);
void printArray(double* array, int size);
void initResultMatrix(double* result, int size);

FILE* aFile;
FILE* resultFile;


int main(int argc, char *argv[])
{

time_t start,end;

    if (argc != 2)
    {
    	printf("Error! please enter the correct number of command arguments\n");
    	exit(1);
    }

    char* a = argv[1];
    char* result = "output.txt";


    aFile = readFile(a, "r");
    resultFile = readFile(result, "w");
    int size;

    // Get the dimensions of the a matrix
    fscanf(aFile, "%d,", & size);


	double *aArray = (double *) malloc(sizeof(double) * size);




    fileToArray(aFile, aArray, size);



	start=clock();


    sequentialScan(aArray, size);

	end=clock();
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);


    arrayToFile(resultFile, aArray, size);

	free(aArray);

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

// Reads the files and store the numbers in an array
void fileToArray(FILE* file, double* array, int size)
{
	for (int i = 0; i < size; i++)
	{				
		fscanf(file, "%lf,",&array[i]); 			
	}
	fclose(file);
}



void sequentialScan(double *array, int size)
{

    for (int i = 1; i < size; i++)
    {
        array[i] = array[i] + array[i - 1];
    }

}


void printArray(double* array, int size)
{	

    for (int i = 0; i < size; i++)
  	{    
	    printf("%lf   ", array[i]);   
  	}
}

// Prints the matrix to a file
void arrayToFile(FILE* file, double* array, int size)
{
	fprintf(file, "%d\n", size);
	for (int i = 0; i < size; i++)
	{
		fprintf(file, "%lf   ", array[i]);
	}
	fclose(file);

}

void initResultMatrix(double* result, int size)
{
    for (int i = 0; i < size;i++)
    {
        result[i] = 0;
    }
}



int IDX(int row, int col, int LDA){return row*LDA+col;}
