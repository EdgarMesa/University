#include <stdio.h>
#include <stdlib.h>
#define MAX 100 
#include <time.h>


FILE* readFile(char* filename, char* mode);
void fileToArray(FILE* file, double* array, int h, int w);
void arrayToFile(FILE* file, double* array, int h, int w);
void scaleAndAdd(double a, double* b, double* c,  double* d, int rb, int cb, int cc);
int IDX(int row, int col, int LDA);
void printArray(double* array, int h, int w);

FILE* bFile;
FILE* cFile;
FILE* dFile;




int main(int argc, char *argv[])
{

time_t start,end;

    if (argc != 4)
    {
    	printf("Error! please enter the correct number of command arguments\n");
    	exit(1);
    }

    double a = atof(argv[1]);
    char* b = argv[2];
    char* c = argv[3];
    char* d = "d.txt";

    bFile = readFile(b, "r");
    cFile = readFile(c, "r");
    dFile = readFile(d, "w");
    int yDimension_b, xDimension_b, yDimension_c, xDimension_c;

    // Get the dimensions of each matrix
    fscanf(bFile, "%d,", &yDimension_b);
    fscanf(bFile, "%d,", &xDimension_b);
    fscanf(cFile, "%d,", &yDimension_c);
    fscanf(cFile, "%d,", &xDimension_c);

    // Make sure we can multiply both matrices
    if(xDimension_b != yDimension_c)
    {
    	printf("Error! the number of columns of the first matrix %dx%d must be the same as the number of rows as the second matrix %dx%d\n", yDimension_b, xDimension_b, yDimension_c, xDimension_c);
    	exit(1);
    }

	double *bArray = (double *) malloc(sizeof(double) * yDimension_b * xDimension_b);
	double *cArray = (double *) malloc(sizeof(double) * yDimension_c * xDimension_c);
	double *dArray = (double *) malloc(sizeof(double) * yDimension_b * xDimension_c);



	// initialize the result matrix
	for(int i = 0; i < yDimension_b * xDimension_c; i++)
	{
		dArray[i] = 0.0;
	}


    fileToArray(bFile, bArray, yDimension_b, xDimension_b);
    fileToArray(cFile, cArray, yDimension_c, xDimension_c);


	start=clock();


    scaleAndAdd(a, bArray, cArray, dArray, yDimension_b, xDimension_b, xDimension_c);
	end=clock();
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);


    arrayToFile(dFile, dArray, yDimension_c, xDimension_b);
	free(bArray);
	free(cArray);
	free(dArray);

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



void scaleAndAdd(double a, double* b, double* c,  double* d, int rb, int cb, int cc)
{

	// Multiply the b and c matrices and store them in d (dot product)
	for (int i = 0; i < rb; ++i) 
	{
	  for (int j = 0; j < cc; ++j) 
	  {
	     for (int k = 0; k < cb; ++k) 
	     {
	     	int idx1=IDX(i,k,cb); // Index for the first matrix
	     	int idx2=IDX(k,j,cc); // Index for the second
	     	int idx3 = IDX(i,j,cc); // Index for the result matrix. Notice that (m x n) * (n x k) = (m x k)

	     	//printf("\n%f  x  %f + %f -----> %d  x  %d + %d\n",b[idx1], c[idx2], d[idx3], idx1, idx2, idx3 );
	        d[idx3] += b[idx1] * c[idx2];
	     }
	  }
	}

	 // Sum a to all the numbers of the matrix
	for (int i = 0; i < rb; ++i) 
	{
	    for (int j = 0; j < cc; ++j) 
	    {
	      int idx=IDX(i,j,cc);
	       d[idx] += a;
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












