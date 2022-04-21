BUILD_FILES := sdlImagePrint sdlImageShift cudaImageShift

all:$(BUILD_FILES)

%: %.c
	gcc $< -Wall -std=c99 -O3 -lSDL2 -lSDL2_image -o $@

%: %.cu
	nvcc $< -lSDL2 -lSDL2_image -o $@

.PHONY: clean
clean:
	rm -f *.o $(BUILD_FILES)
