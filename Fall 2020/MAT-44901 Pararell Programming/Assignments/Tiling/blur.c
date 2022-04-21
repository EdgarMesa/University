#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <stdio.h>
#include <time.h>
#include <stdbool.h>

// Cleans up and exits
void cleanupAndClose(int exitCode);

// Load a given image
SDL_Surface* loadImage(char *filename);

// blur image by taking the average value of the pixels nerby
void blur(SDL_Surface *image, SDL_Surface *copy, int a);

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

int main(int argc, char *argv[])
{
    // Check that we have the right number of args.
    if(argc != 4)
    {
        fprintf(stderr, "Usage: %s source \n", argv[0]);
        cleanupAndClose(EXIT_FAILURE);
    }
    char *name;
    time_t start,end;
    
    int a = atoi(argv[1]);

    if (a < 0 || a > 9)
    {
        fprintf(stderr, "a value must be > 0 and <= 9. Value given %d \n", a);
        cleanupAndClose(EXIT_FAILURE);
    }
    checkSDL(source = loadImage(argv[2]));
    copy = SDL_ConvertSurface(source, source->format, SDL_SWSURFACE);
    name = argv[3];

    if (strlen(name) > 1020)
    {
        fprintf(stderr, "Use a smaller name for the output image below 1020 characters\n");
        cleanupAndClose(EXIT_FAILURE);
    }
    strcpy(destination, name);
    strcat(destination, ".png");

    printf("Blurring image\n");

    start=clock();

    blur(source, copy, a);

    end=clock();
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);

	// Save image
    printf("Saving: %s\n",destination);
    IMG_SavePNG(source, destination);
    

    cleanupAndClose(EXIT_SUCCESS);
    return EXIT_SUCCESS;
}

void cleanupAndClose(int exitCode)
{
    if(source) SDL_FreeSurface(source);
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

void blur(SDL_Surface *image, SDL_Surface *copy, int a)
{
	Uint32 *pixels = (Uint32 *)image->pixels;
	Uint32 *pixelsCopy = (Uint32 *)copy->pixels;

    for(int y = 0; y < image->h; y++)
    {
        for(int x = 0; x < image->w; x++)
        {
            // Get the colors of the actual pixel in case we do not need to take any average. case a = 0.
            Uint32 pixelActual = pixelsCopy[(y * copy->w) + x];
            Uint32 tempR = pixelActual & copy->format->Rmask;
            Uint32 tempG = (pixelActual & copy->format->Gmask) >> copy->format->Gshift;
            Uint32 tempB = (pixelActual & copy->format->Bmask) >> copy->format->Bshift;
            Uint32 tempA = (pixelActual & copy->format->Amask) >> copy->format->Ashift;
            int pixelsNumber = 1; // total amount of pixels for the average

            // Start from y - a rows and a -x columns. 
            for(int ii = y - a; ii < (y - a) + (2 * a + 1); ii++)
            {
                for(int jj = x - a; jj < (x - a) + (2 * a + 1); jj++)
                {
                    // if inside the boundaries and not the actual pixel because we already took it.
                    if (ii > -0 && ii < copy->h && jj > -1 && jj < copy->w && ii!=y && jj!=x)
                    {
                        
                        pixelsNumber++;
                        Uint32 pixel = pixelsCopy[(ii * copy->w) + jj];
                        Uint32 r = pixel & copy->format->Rmask; // Isolate red component
                        tempR += r;                              // Sum for the average of red component
                        r = r >> copy->format->Rshift;          // Shift it down
                        Uint32 g = pixel & copy->format->Gmask; // Isolate green component
                        g = g >> copy->format->Gshift;          // Shift it down
                        tempG += g;                              // Sum for the average of green component
                        Uint32 b = pixel & copy->format->Bmask; // Isolate blue component
                        b = b >> copy->format->Bshift;          // Shift it down
                        tempB += b;                              // Sum for the average of blue component

                    }
                }
                
                
            }
            


            // Average by the number of pixels of the grid
            tempR = (Uint32) (tempR / (Uint32) pixelsNumber);
            tempG = (Uint32) (tempG / (Uint32) pixelsNumber);
            tempB = (Uint32) (tempB / (Uint32) pixelsNumber);


			// Build the shifted pixel
			pixels[(y * image->w) + x] = (tempR << image->format->Rshift) |
										 (tempG << image->format->Gshift) |
										 (tempB << image->format->Bshift) |
										 (tempA << image->format->Ashift);
        }
    }
}