#include <stdio.h>
#include <stdlib.h>
FILE* readFile(char* filename, char* mode);

int main(int argc, char *argv[])
{
    if (argc != 5)
    {
    	printf("Error! please enter the correct number of command arguments\n");
    	exit(1);
    }

    int ROWS_a = atoi(argv[1]);
    int COLUMNS_a = atoi(argv[2]);
    int ROWS_b =  atoi(argv[3]);
    int COLUMNS_b =  atoi(argv[4]);

    FILE* aFile;
    FILE* bFile;
    FILE* cFile;

    aFile = readFile("a.txt", "w");
    bFile = readFile("b.txt", "w");
    cFile = readFile("c.txt", "w");

    const int max = 10000;
    fprintf(aFile, "%d ", ROWS_a);
    fprintf(aFile, "%d\n", COLUMNS_a);
    fprintf(bFile, "%d ", ROWS_b);
    fprintf(bFile, "%d\n", COLUMNS_b);
    
    // Make sure we can multiply both matrices
    if(COLUMNS_a != ROWS_b)
    {
    	printf("Error! the number of columns of the first matrix %dx%d must be the same as the number of rows as the second matrix %d x %d\n", ROWS_a, COLUMNS_a, ROWS_b, COLUMNS_b);
    	exit(1);
    }

    for(int i= 0; i < ROWS_a; i++)
    {
        for(int j = 0; j < COLUMNS_a; j++)
        {
            fprintf(aFile,"%.10f   ", ((float)rand()/(float)(RAND_MAX)) * max);
        }
         fprintf(aFile,"\n");
    }

    for(int i= 0; i < ROWS_b; i++)
    {
        for(int j = 0; j < COLUMNS_b; j++)
        {
            fprintf(bFile,"%.10f   ", ((float)rand()/(float)(RAND_MAX)) * max);
        }
         fprintf(bFile,"\n");
    }

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




