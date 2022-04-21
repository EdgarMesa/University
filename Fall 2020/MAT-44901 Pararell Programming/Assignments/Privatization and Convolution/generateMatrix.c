#include <stdio.h>
#include <stdlib.h>
FILE* readFile(char* filename, char* mode);

int main(int argc, char *argv[])
{
    if (argc < 2 || argc > 3)
    {
    	printf("Error! please enter the correct number of command arguments\n");
    	exit(1);
    }


    FILE* aFile;
    aFile = readFile("a.txt", "w");


    if (argc == 2)
    {
        int size = atoi(argv[1]);




        const int max = 10000;
        fprintf(aFile, "%d\n", size);

        

        for(int i= 0; i < size; i++)
        {

            fprintf(aFile,"%.10f   ", ((float)rand()/(float)(RAND_MAX)) * max);

        }
    }
    else
    {
        int ROWS_a = atoi(argv[1]);
        int COLUMNS_a = atoi(argv[2]);




        const int max = 10000;
        fprintf(aFile, "%d ", ROWS_a);
        fprintf(aFile, "%d\n", COLUMNS_a);

        

        for(int i= 0; i < ROWS_a; i++)
        {
            for(int j = 0; j < COLUMNS_a; j++)
            {
                fprintf(aFile,"%.10f   ", ((float)rand()/(float)(RAND_MAX)) * max);
            }
            fprintf(aFile,"\n");
        }
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




