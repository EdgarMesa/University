#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <stdio.h>
#include <time.h>
#include <stdbool.h>

// Cleans up and exits
void cleanupAndClose(int exitCode);

// Load a given image
SDL_Surface* loadImage(char *filename);

// grayPixel[index] = 0.21*r + 0.71*g + 0.07*b
void grayscale(SDL_Surface *image);

void checkSDL(void* result)
{
    if(result == NULL)
    {
        fprintf(stderr, "Error: %s\n", SDL_GetError());
        cleanupAndClose(EXIT_FAILURE);
    }
}

SDL_Surface *source;
char destination[1024];

int main(int argc, char *argv[])
{
    // Check that we have the right number of args.
    if(argc != 3)
    {
        fprintf(stderr, "Usage: %s source \n", argv[0]);
        cleanupAndClose(EXIT_FAILURE);
    }
    char *name;
    time_t start,end;
    
    checkSDL(source = loadImage(argv[1]));
    name = argv[2];

    if (strlen(name) > 1020)
    {
        fprintf(stderr, "Use a smaller name for the output image below 1020 characters\n");
        cleanupAndClose(EXIT_FAILURE);
    }

    // Add extencion to the filename.
    strcpy(destination, name);
    strcat(destination, ".png");

    printf("Gray scaling\n");

    start=clock();
    grayscale(source);

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

void grayscale(SDL_Surface *image)
{
	Uint32 *pixels = (Uint32 *)image->pixels;
    for(int y = 0; y < image->h; y++)
    {
        for(int x = 0; x < image->w; x++)
        {
        	Uint32 pixel = pixels[(y * image->w) + x];
        	Uint32 r = pixel & image->format->Rmask; // Isolate red component
			r = r >> image->format->Rshift;          // Shift it down
        	Uint32 g = pixel & image->format->Gmask; // Isolate green component
			g = g >> image->format->Gshift;          // Shift it down
        	Uint32 b = pixel & image->format->Bmask; // Isolate blue component
			b = b >> image->format->Bshift;          // Shift it down
            Uint32 a = pixel & image->format->Amask; // Isolate alpha component
            a = a >> image->format->Ashift;          // Shift it down
            Uint32 v = 0.212671f * r + 0.715160f * g + 0.072169f * b; //Gray scale formula

			// Build the shifted pixel
			pixels[(y * image->w) + x] = (v << image->format->Rshift) |
										 (v << image->format->Gshift) |
										 (v << image->format->Bshift) |
										 (a << image->format->Ashift);
        }
    }
}
