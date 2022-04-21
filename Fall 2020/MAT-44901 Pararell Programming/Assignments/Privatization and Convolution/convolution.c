#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <stdio.h>
#include <time.h>
#include <stdbool.h>




FILE* readFile(char* filename, char* mode);
void fileToArray(FILE* file, double* array, int h, int w);
int IDX(int row, int col, int LDA);


// Cleans up and exits
void cleanupAndClose(int exitCode);

// Load a given image
SDL_Surface* loadImage(char *filename);

// convolution of an image with a given kernel
void convolution(SDL_Surface *image, SDL_Surface *copy,  double *kernel_mask, int kernelWidth);

void checkSDL(void* result)
{
    if(result == NULL)
    {
        fprintf(stderr, "Error: %s\n", SDL_GetError());
        cleanupAndClose(EXIT_FAILURE);
    }
}

SDL_Surface *source;
SDL_Surface *copy;
char destination[1024];
FILE* kernelFile;
double *kernelArray;


int main(int argc, char *argv[])
{
    // Check that we have the right number of args.
    if(argc != 4)
    {
        fprintf(stderr, "Usage: %s wrong number of arguments \n", argv[0]);
        cleanupAndClose(EXIT_FAILURE);
    }
    char *name;
    char *kernel;
    time_t start,end;
    

    checkSDL(source = loadImage(argv[1]));
    copy = SDL_ConvertSurface(source, source->format, SDL_SWSURFACE);

    kernel = argv[2];
    kernelFile = readFile(kernel, "r");
    name = argv[3];

    int kernelWidth;
    // Get the dimensions of the kernel.
    fscanf(kernelFile, "%d,", &kernelWidth);
    fscanf(kernelFile, "%d,", &kernelWidth);

    // Allocate space for kernel array.
    kernelArray = (double *) malloc(sizeof(double) * kernelWidth * kernelWidth);

    fileToArray(kernelFile, kernelArray, kernelWidth, kernelWidth);

    if (strlen(name) > 1020)
    {
        fprintf(stderr, "Use a smaller name for the output image below 1020 characters\n");
        cleanupAndClose(EXIT_FAILURE);
    }
    strcpy(destination, name);
    strcat(destination, ".png");

    printf("Convoluting image...\n");

    start=clock();
    convolution(source, copy, kernelArray, kernelWidth);

    end=clock();
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);

	// Save image
    printf("Saving: %s\n",destination);
    IMG_SavePNG(copy, destination);
    

    cleanupAndClose(EXIT_SUCCESS);
    return EXIT_SUCCESS;


}

void cleanupAndClose(int exitCode)
{
    if(source) SDL_FreeSurface(source);
    if(kernelArray) free(kernelArray);
    if(copy) SDL_FreeSurface(copy);

    exit(exitCode);
}

SDL_Surface* loadImage(char *filename)
{
    printf("Loading: %s\n", filename);

    // Load the image into memory
    SDL_Surface *image = IMG_Load(filename);

    // Make sure pixel values are 32-bit
    if(image)
    {
    	SDL_Surface *temp = SDL_ConvertSurfaceFormat(image, SDL_PIXELFORMAT_RGBA32, 0);
    	SDL_FreeSurface(image);
    	image = temp;
    }

    return image;
}

void convolution(SDL_Surface *image, SDL_Surface *copy,  double *kernel_mask, int kernelWidth)
{


    // image pixels.
    Uint32 *pixels = (Uint32 *) image->pixels;
    Uint32 *pixelsCopy = (Uint32 *) copy->pixels;
    int maskOffset = (kernelWidth / 2);

    
	int starting_r;
    int starting_c;

    // for each row of the image.
    for (int i = 0; i < image->h; i++)
    {
        // for each column of the image.
        for (int j = 0; j < image->w; j++)
        {
        
            Uint32 pixelActual = pixels[i * image->w + j];
            long tempR = 0;
            long tempG = 0;
            long tempB = 0;

            Uint32 tempA = (pixelActual & image->format->Amask) >> image->format->Ashift;

            // over each row of mask.
            for (int rowMask = 0; rowMask < kernelWidth; rowMask++)
            {
                // starting positions of rows.
                starting_r = i - maskOffset + rowMask;

                for (int columnMask = 0; columnMask < kernelWidth; columnMask++)
                {
                    // starting positions of columns.
                    starting_c = j - maskOffset + columnMask;

                    // boundary check for the input array.
                    if (starting_r >= 0 && starting_r < image->h && starting_c >= 0 && starting_c < image->w)
                    {

                        double kernelValue = kernel_mask[rowMask * kernelWidth + columnMask]; 
                        Uint32 pixel = pixels[starting_r * image->w + starting_c];
                        Uint32 r = pixel & image->format->Rmask;                    // Isolate red component
                        tempR += r * kernelValue;                                   // Sum for the average of red component
                        r = r >> image->format->Rshift;                             // Shift it down
                        Uint32 g = pixel & image->format->Gmask;                    // Isolate green component
                        g = g >> image->format->Gshift;                             // Shift it down
                        tempG += g * kernelValue;                                   // Sum for the average of green component
                        Uint32 b = pixel & image->format->Bmask;                    // Isolate blue component
                        b = b >> image->format->Bshift;                             // Shift it down
                        tempB += b * kernelValue;  


                    }

                }
            }
            
            // avoid overflow.
            if (tempR > 255){tempR = 255;}
            else if (tempR < 0){tempR = 0;}
            if (tempG > 255){tempG = 255;}
            else if (tempG < 0){tempG = 0;}
            if (tempB > 255){tempB = 255;}
            else if (tempB < 0){tempB = 0;}

            // rebuilt the pixel.
			pixelsCopy[i * copy->w + j] = ((Uint32) tempR << copy->format->Rshift) |
										 ((Uint32) tempG << copy->format->Gshift) |
										 ((Uint32) tempB << copy->format->Bshift) |
										 (tempA << copy->format->Ashift);
			    

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


int IDX(int row, int col, int LDA){return row*LDA+col;}