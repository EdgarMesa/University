#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <stdio.h>
#include <time.h>
#include <stdbool.h>

// Cleans up and exits
void cleanupAndClose(int exitCode);

// Load a given image
SDL_Surface* loadImage(char *filename);

// Print out image data
void printImage(SDL_Surface *image);

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

    printImage(source);

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

void printImage(SDL_Surface *image)
{
    printf("Width = %d\n", image->w);
    printf("Height = %d\n", image->h);
    printf("Masks = (%08x, %08x, %08x, %08x)\n",
        image->format->Rmask, image->format->Gmask, image->format->Bmask, image->format->Amask);
    printf("Shifts = (%d, %d, %d, %d)\n",
        image->format->Rshift, image->format->Gshift, image->format->Bshift, image->format->Ashift);

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
            printf("(%d, %d) = (%3d, %3d, %3d, %3d)\n", x, y, r, g, b, a);
        }
    }
}
