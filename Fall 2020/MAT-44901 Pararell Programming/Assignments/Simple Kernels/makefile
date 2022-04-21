BUILD_FILES := matrixMultiply generateMatrix cudaDeviceInfo cudaMatrixMultiply cudaTiledMatrixMultiply averageImages cudaAverageImages tiltedBlur

all:$(BUILD_FILES)

%: %.c
	gcc $< -Wall -std=c99 -lSDL2 -lSDL2_image -o $@

%: %.cu
	nvcc $< -Xcompiler -rdynamic -lineinfo -lSDL2 -lSDL2_image -o $@

.PHONY: test
test:
	./generateMatrix 200 200 200 200
	./matrixMultiply a.txt b.txt c.txt
	nvprof ./cudaMatrixMultiply a.txt b.txt c.txt
	nvprof ./cudaTiledMatrixMultiply a.txt b.txt c.txt
	cd Pictures/; ls | xargs ./../averageImages ; ls | xargs cuda-memcheck ./../cudaAverageImages


.PHONY: clean
clean:
	rm -f *.o $(BUILD_FILES) *.txt *.png



