all: ic primeSearch

ExamIC.o: ExamIC.cu
	nvcc -c ExamIC.cu

ic: ic.o ExamIC.o
	nvcc -o ic ic.o ExamIC.o

ExamPrime.o: ExamPrime.cu
	nvcc -c ExamPrime.cu

primeSearch: ExamPrime.o
	nvcc -o primeSearch ExamPrime.o -Xcompiler -rdynamic -lineinfo -lm

.PHONY: clean
clean:
	rm -f a.out ExamIC.o ic ExamPrime.o primeSearch
