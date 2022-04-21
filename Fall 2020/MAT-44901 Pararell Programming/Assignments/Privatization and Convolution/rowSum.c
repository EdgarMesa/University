#include <stdio.h>
#include <stdlib.h>
#include <time.h>


FILE* readFile(char* filename, char* mode);
void fileToArray(FILE* file, double* array, int h, int w);
void arrayToFile(FILE* file, double* array, int h, int w);
void addRows(double* a , double* result, int ar, int ac);
int IDX(int row, int col, int LDA);
void printArray(double* array, int h, int w);
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
    int yDimension_a, xDimension_a;

    // Get the dimensions of the a matrix
    fscanf(aFile, "%d,", &yDimension_a);
    fscanf(aFile, "%d,", &xDimension_a);


	double *aArray = (double *) malloc(sizeof(double) * yDimension_a * xDimension_a);
	double *resultArray = (double *) malloc(sizeof(double) * yDimension_a);




    fileToArray(aFile, aArray, yDimension_a, xDimension_a);

    initResultMatrix(resultArray, yDimension_a);


	start=clock();


    addRows(aArray, resultArray, yDimension_a, xDimension_a);

	end=clock();
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);


    for (int i = 0; i < yDimension_a; i++)
    {


        fprintf(resultFile, "%lf   ", resultArray[i]);



    }
    fclose(resultFile);



	free(aArray);
	free(resultArray);

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
void fileToArray(FILE* file, double* array, int h, int w)
{
	for (int i = 0; i < h; i++)
	{
		for(int j = 0; j < w; j++)
		{
			
			fscanf(file, "%lf,",&array[IDX(i, j, w)]); 
			
		}
	}
	fclose(file);
}



void addRows(double* a, double* result, int ar, int ac)
{

	// add each row of the matrix and store it in an output file
	for (int i = 0; i < ar; ++i) 
	{
        double sum = 0;
		for (int j = 0; j < ac; ++j) 
		{
            int idx = IDX(i, j, ac);
			
            sum += (double) a[idx];
			
		}
        result[i] = sum;
	}

}


void printArray(double* array, int h, int w)
{	

    for (int i = 0; i < h; i++)
  	{
	    for(int j = 0; j < w; j++)
	    {
	      
	      printf("%lf   ", array[IDX(i, j, w)]);
	      
	    }
    	printf("\n");
  	}
}

// Prints the matrix to a file
void arrayToFile(FILE* file, double* array, int h, int w)
{
	fprintf(file, "%d ", h);
	fprintf(file, "%d\n", w);
	for (int i = 0; i < h; i++)
	{
		for(int j = 0; j < w; j++)
		{

			fprintf(file, "%lf   ", array[IDX(i, j, w)]);

		}

		fprintf(file, "\n");

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
