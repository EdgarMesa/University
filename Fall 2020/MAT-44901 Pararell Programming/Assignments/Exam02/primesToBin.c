#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>


FILE* readFile(char* filename, char* mode);
void fileToArray(FILE* file, long long* array, int size);
void printArray(long long* array, int size);
void binFileToArray(FILE* file, long long* array, int size);
long long insertDigit(long long prime, long long digit, int location);

FILE* file;
FILE* binFile;
FILE* readbin;
int main(int argc, char *argv[])
{
    int size = 1000000000LL;
    file = readFile("primes.txt","r");
	binFile = readFile("primes.bin", "wb");
	

	printf("Allocating space...\n");

    //long long *array = (long long *) malloc(sizeof(long long) * size);
    long long *arraybin = (long long *) malloc(sizeof(long long) * size);
    long long *result = (long long *) malloc(sizeof(long long) * 11);
	long long TEST_LIMIT = 1000000L;

	time_t start,end;

    // fileToArray(file, array, size);

	// fwrite(array, sizeof(long long), size, binFile);
	// fclose(binFile);
	readbin = readFile("../../../exam02/primes.bin", "rb");

	printf("Loading...\n");
	binFileToArray(readbin, arraybin, size);


	start=clock();

	printf("Calculating...\n");	
	for (int n = 0; n < TEST_LIMIT;n++)
	{
		long long prime = arraybin[n];
		long long count = 0;

		for (long long i = 0; i < 10;i++)
			{

				int len = (int) ceil(log10(prime));

				for(int location = 0; location <= len; location++)
				{
					int brk = 0;
					long long newNumber = insertDigit(prime, i, location);

					long long low = 0;
					long long hi = size - 1;


					while (low <= hi && newNumber != prime)
					{
						long long mid = low + (hi - low) / 2;

						if (newNumber < arraybin[mid]){hi = mid - 1;}
						else if (newNumber > arraybin[mid]){low = mid + 1;}

						else
						{	
							brk = 1;
							break;
						}
						
					}

					if (brk == 1){break;}
					if(location == len){count++;}

				}
			}

		result[count]++;

	}

	end=clock();
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);


	for (int i = 0; i < 11;i++)
	{
		printf("%d --> %lld\n", i, result[i]);
	}

	free(arraybin);
	free(result);

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
void fileToArray(FILE* file, long long* array, int size)
{
	for (int i = 0; i < size; i++)
	{
		
			
        fscanf(file, "%lld,",&array[i]); 
			

	}
	fclose(file);
}


void printArray(long long* array, int size)
{	

    for (int i = 0; i < size; i++)
  	{
	   	      
	      printf("%lld   \n", array[i]);
	      
  	}
}

void binFileToArray(FILE* file, long long* array, int size)
{
	for (int i = 0; i < size;i++)
	{
		fread(&array[i], sizeof(long long), 1, file);
	}
	
}

long long insertDigit(long long prime, long long digit, int location)
{
    int digits[12];
    int length = 0;
    int added = 0;

    while(prime > 0)
    {
        if (length == location)
        {
            digits[length++] = digit;
            added = 1;
        }

        digits[length++] = prime % 10;
        prime /= 10;
    }
    if(added == 0){digits[length++] = digit;}

    long long result = 0;
    for(int i = length -1 ; i >=0;i--)
    {
        result = result * 10 + digits[i];
    }

    return result;
}




