#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <stdio.h>
#include <time.h>
#include <stdbool.h>

// Cleans up and exits
void cleanupAndClose(int exitCode, int N);

// Load a given image
SDL_Surface* loadImage(char *filename);

// Averaging all the pixels of a image collection
void average(SDL_Surface **images, SDL_Surface *output, int N);

void checkSDL(void* result, int N)
{
    if(result == NULL)
    {
        fprintf(stderr, "Error: %s\n", SDL_GetError());
        cleanupAndClose(EXIT_FAILURE, N);
    }
}

SDL_Surface **sources;
SDL_Surface *output;

int main(int argc, char *argv[])
{   
    // Number of pictures in the array, first argument does not 
    // count and first picture will be the start of the output image.
    int N = argc - 2;
    char *outputname = "output.png";

    // Check that we have the right number of args.
    if(argc < 2)
    {
        fprintf(stderr, "Usage: %s source \n", argv[0]);
        cleanupAndClose(EXIT_FAILURE, N);
    }


    sources = (SDL_Surface **) malloc(sizeof(SDL_Surface*) * N);

    time_t start,end;

    printf("Loading Images\n");

    // Loading all the pictures
    checkSDL(output = loadImage(argv[1]), N);
    for(int i = 0; i < N; i++)
    {   
        checkSDL(sources[i] = loadImage(argv[i + 2]), N);
    }
    printf("Images Loaded\n");

    printf("Averaging images\n");

    start=clock();

    average(sources, output, N);

    end=clock();
	double t =  (double) (end-start) / CLOCKS_PER_SEC;
	printf("It took: %lfs\t %lfms\t %lfμs\n",t, t * 1000, t*1000000);

	// Save image
    printf("Saving: %s\n",outputname);
    IMG_SavePNG(output, outputname);
    
    cleanupAndClose(EXIT_SUCCESS, N);
    return EXIT_SUCCESS;
}

void cleanupAndClose(int exitCode, int N)
{   

    for(int i = 1; i < N; i++)
    {
        if(sources[i]) SDL_FreeSurface(sources[i]);   
    }

    if(sources) free(sources);
    if(output) SDL_FreeSurface(output);


     
    exit(exitCode);
}

SDL_Surface* loadImage(char *filename)
{
    
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

void average(SDL_Surface **images, SDL_Surface *output, int N)
{
	
    Uint32 *pixelsOutput = (Uint32 *)output->pixels;

    // For each picture
    for(int i = 0; i < N; i++)
    {   
        Uint32 *pixels = (Uint32 *)images[i]->pixels;
        for(int y = 0; y < images[i]->h; y++)
        {
            for(int x = 0; x < images[i]->w; x++)
            {   
                Uint32 pixel = pixels[(y * images[i]->w) + x];
                Uint32 outputPixel = pixelsOutput[(y * output->w) + x];

                Uint32 r = pixel & images[i]->format->Rmask;        // Isolate red component
                Uint32 ro = outputPixel & output->format->Rmask; 
                r = r >> images[i]->format->Rshift;                 // Shift it down
                ro = ro >> output->format->Rshift;    

                Uint32 g = pixel & images[i]->format->Gmask;        // Isolate green component
                Uint32 go = outputPixel & output->format->Gmask;    
                g = g >> images[i]->format->Gshift;                 // Shift it down
                go = go >> output->format->Gshift;      

                Uint32 b = pixel & images[i]->format->Bmask;        // Isolate blue component
                Uint32 bo = outputPixel & output->format->Bmask;
                b = b >> images[i]->format->Bshift;                 // Shift it down
                bo = bo >> output->format->Bshift;   

                Uint32 ao = outputPixel & output->format->Amask;    // Isolate alpha component
                ao = ao >> output->format->Ashift;                  // Shift it down

               
                
                // Average the pixels. This formulas comes because as it is explain in the XOR/AND algorithm to calculate averages will not cause overflows between the colour channels, 
                // but the "shift-right" instruction may cause an underflow: the low bit of, for example, the green channel may "flow into" the high bit of the blue channel. 
                // Fortunately, this is easy to correct by masking of the low bits of the three channels before the "shift right" instruction
                ro = ((((ro) ^ (r)) & 0xfffefefeL) >> 1) + ((ro) & (r));
                go = ((((go) ^ (g)) & 0xfffefefeL) >> 1) + ((go) & (g));
                bo = ((((bo) ^ (b)) & 0xfffefefeL) >> 1) + ((bo) & (b));

    
                // Build the shifted pixel
                pixelsOutput[(y * output->w) + x] = (ro << images[i]->format->Rshift) |
                                            (go << images[i]->format->Gshift) |
                                            (bo << images[i]->format->Bshift) |
                                            (ao << images[i]->format->Ashift);
            }
        }
        
    }
   
    
}