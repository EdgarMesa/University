BUILD_FILES :=  generateMatrix rowSum cudaSumRows cudaSumRowsCooperate convolution cudaConvolution frequencyCounter cudaFrequencyCounterPrivatization cudaFrequencyCounterTiling scan cudaNaiveScan cudaOptimazedScan

all:$(BUILD_FILES)

%: %.c
	gcc $< -Wall -std=c99 -lSDL2 -lSDL2_image -o $@

%: %.cu
	nvcc $< -Xcompiler -rdynamic -lineinfo -lSDL2 -lSDL2_image -o $@

.PHONY: test
test:
	./generateMatrix 2500 2500
	./rowSum a.txt
	./cudaSumRows a.txt
	./cudaSumRowsCooperate a.txt
	./frequencyCounter texts/text4.txt
	./cudaFrequencyCounterPrivatization texts/text4.txt
	./cudaFrequencyCounterTiling texts/text4.txt
	./convolution pictures/picture4.jpg "kernels/Gaussian blur 5x5.txt" output
	./cudaConvolution pictures/picture4.jpg "kernels/Gaussian blur 5x5.txt" output2
	./generateMatrix 10000000
	./scan a.txt
	./cudaNaiveScan a.txt
	./cudaOptimazedScan a.txt



.PHONY: clean
clean:
	rm -f *.o $(BUILD_FILES) *.txt *.png



