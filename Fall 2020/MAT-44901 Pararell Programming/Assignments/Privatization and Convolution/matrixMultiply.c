#include <stdio.h>
#include <stdlib.h>
#include <time.h>


FILE* readFile(char* filename, char* mode);
void fileToArray(FILE* file, double* array, int h, int w);
void arrayToFile(FILE* file, double* array, int h, int w);
void multiplyMatrices(double* a, double* b, double* c, int ar, int ac, int bc);
int IDX(int row, int col, int LDA);
void printArray(double* array, int h, int w);

FILE* aFile;
FILE* bFile;
FILE* cFile;


int main(int argc, char *argv[])
{

time_t start,end;

    if (argc != 4)
    {
    	printf("Error! please enter the correct number of command arguments\n");
    	exit(1);
    }

    char* a = argv[1];
    char* b = argv[2];
    char* c = argv[3];

    aFile = readFile(a, "r");
    bFile = readFile(b, "r");
    cFile = readFile(c, "w");
    int yDimension_a, xDimension_a, yDimension_b, xDimension_b;

    // Get the dimensions of each matrix
    fscanf(aFile, "%d,", &yDimension_a);
    fscanf(aFile, "%d,", &xDimension_a);
    fscanf(bFile, "%d,", &yDimension_b);
    fscanf(bFile, "%d,", &xDimension_b);

    // Make sure we can multiply both matrices
    if(xDimension_a != yDimension_b)
    {
    	printf("Error! the number of columns of the first matrix %dx%d must be the same as the number of rows as the second matrix %dx%d\n", yDimension_a, xDimension_a, yDimension_b, xDimension_b);
    	exit(1);
    }

	double *aArray = (double *) malloc(sizeof(double) * yDimension_a * xDimension_a);
	double *bArray = (double *) malloc(sizeof(double) * yDimension_b * xDimension_b);
	double *cArray = (double *) malloc(sizeof(double) * yDimension_a * xDimension_b);




    fileToArray(aFile, aArray, yDimension_a, xDimension_a);
    fileToArray(bFile, bArray, yDimension_b, xDimension_b);


	start=clock();


    multiplyMatrices(aArray, bArray, cArray, yDimension_a, xDimension_a, xDimension_b);
	end=clock();
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);


    arrayToFile(cFile, cArray, yDimension_a, xDimension_b);
	free(aArray);
	free(bArray);
	free(cArray);

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



void multiplyMatrices(double* a, double* b, double* c, int ar, int ac, int bc)
{

	// Multiply the a and b matrices and store them in d (dot product)
	for (int i = 0; i < ar; ++i) 
	{

		for (int j = 0; j < bc; ++j) 
		{
			double Pvalue = 0;
			int idx3 = IDX(i,j,bc); // Index for the result matrix. Notice that (m x n) * (n x k) = (m x k)
			for (int k = 0; k < ac; ++k) 
			{
				
				int idx1=IDX(i,k,ac); // Index for the first matrix
				int idx2=IDX(k,j,bc); // Index for the second
				

				//printf("\n%f  x  %f + %f -----> %d  x  %d + %d\n",b[idx1], c[idx2], d[idx3], idx1, idx2, idx3 );
				Pvalue += a[idx1] * b[idx2];
			}

			c[idx3] = Pvalue;
		}
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



int IDX(int row, int col, int LDA){return row*LDA+col;}












