#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <stdio.h>
#include <time.h>
#include <stdbool.h>

// Cleans up and exits
void cleanupAndClose(int exitCode);

// Load a given image
SDL_Surface* loadImage(char *filename);

// shift red -> green -> blue -> red in the given image
void shiftImage(SDL_Surface *image);

inline void checkSDL(void* result)
{
    if(result == NULL)
    {
        fprintf(stderr, "Error: %s\n", SDL_GetError());
        cleanupAndClose(EXIT_FAILURE);
    }
}

SDL_Surface *source;

int main(int argc, char *argv[])
{
    // Check that we have the right number of args.
    if(argc != 2)
    {
        fprintf(stderr, "Usage: %s source \n", argv[0]);
        cleanupAndClose(EXIT_FAILURE);
    }

    // We not do need to initialize SDL to load image data so we get right to
    // loading the source image.
    checkSDL(source = loadImage(argv[1]));

    printf("Shifting\n");
    shiftImage(source);

	// Save image
    printf("Saving: shiftOut.png\n");
    IMG_SavePNG(source, "shiftOut.png");
    // I have heard that the PNG writer is buggy... I have not had any trouble, but you
    // can also use the BMP writer (hard to mess up BMPs).
    //SDL_SaveBMP(source, "shiftOut.bmp");

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

// A less than optimal shifter...
void shiftImage(SDL_Surface *image)
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

			// Build the shifted pixel
			pixels[(y * image->w) + x] = (b << image->format->Rshift) |
										 (r << image->format->Gshift) |
										 (g << image->format->Bshift) |
										 (a << image->format->Ashift);
        }
    }
}
