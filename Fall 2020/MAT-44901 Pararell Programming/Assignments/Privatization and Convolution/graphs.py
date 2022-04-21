
import numpy as np
import matplotlib.pyplot as plt
import numpy


def slope(x1, y1, x2, y2):
    return (y2-y1)/(x2-x1)

dimensions = [20000, 40000, 60000, 80000, 100000, 120000]
dimensions = [x * x for x in dimensions]
timeSequencial = np.array([2.109, 4.068, 6.03, 7.969, 10.2450, 12.5100])
timeCudaPriv = np.array([0.32608, 0.35389, 0.39086, 0.41299, 0.43234, 0.49123])
timeCudaTiling = np.array([0.004944, 0.0068800, 0.12485, 0.13536, 0.16133, 0.18752])
TIMES = [timeSequencial, timeCudaPriv, timeCudaTiling]
TIMES2 = [timeCudaPriv, timeCudaTiling]

colors = ["blue", "red", "green"]
colors2 = ["red", "green"]
labels = ["Sequencial", "CudaPriv", "CudaTilted"]
labels2 = ["CudaPriv", "CudaTilted"]


for time, color, label in zip(TIMES, colors, labels):
    plt.plot(dimensions, time, label=label,c=color)
    # plt.yscale("log")



slope1, intercept = np.polyfit(np.log(timeSequencial), np.log(dimensions), 1)
slope2, intercept = np.polyfit(np.log(timeCudaPriv), np.log(dimensions), 1)
slope3, intercept = np.polyfit(np.log(timeCudaTiling), np.log(dimensions), 1)



print("Slop Sequencial: ", slope1)
print("Slop CudaPriv: ", slope2)
print("Slop CudaTilted: ", slope3)

plt.xlabel("Total number of characters")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial vs CudaPriv vs CudaTilted Frequency Count")
plt.legend()
plt.savefig("Sequencial vs CudaPriv vs CudaTilted Frequency Count")
plt.show()

for time, color, label in zip(TIMES2, colors2, labels2):
    plt.plot(dimensions, time, label=label,c=color)

plt.xlabel("Total number of characters")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("CudaPriv vs CudaTilted Frequency Count")
plt.legend()
plt.savefig("CudaPriv vs CudaTilted Frequency Count")
plt.show()



plt.clf()
plt.plot(dimensions, timeSequencial, label=labels[0],c=colors[0])
plt.scatter(dimensions, timeSequencial, marker="x",c="black" )
plt.xlabel("Total number of characters")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial Frequency Count")
plt.legend()
plt.savefig("Sequencial Frequency Count")
plt.show()

plt.clf()
plt.plot(dimensions, timeCudaPriv, label=labels[1],c=colors[1])
plt.scatter(dimensions, timeCudaPriv, marker="x",c="black" )

plt.xlabel("Total number of characters")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("CudaPriv Frequency Count")
plt.legend()
plt.savefig("CudaPriv Frequency Count")
plt.show()

plt.clf()
plt.plot(dimensions, timeCudaTiling, label=labels[2],c=colors[2])
plt.scatter(dimensions, timeCudaTiling, marker="x",c="black" )
plt.xlabel("Total number of characters")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("CudaTilted Frequency Count")
plt.legend()
plt.savefig("Tilted Frequency Count")
plt.show()




dimensions = [250000, 1000000, 2510000, 4000000, 6710000, 11000000]
timeSequencial = [1.5620, 4.723, 10.721, 18.95356, 29.909, 42.7230]
timeCuda = [0.31405, 0.736, 0.96428, 1.6120, 1.9527, 2.2306]
timeCudaCoop = [0.22182, 0.042144, 0.072607, 0.10019, 0.14216, 0.1731]

TIMES = [timeSequencial, timeCuda, timeCudaCoop]
TIMES2 = [timeCuda, timeCudaCoop]

colors = ["blue", "red", "green"]
colors2 = ["red", "green"]
labels = ["Sequencial", "Cuda", "CudaCoop"]
labels2 = ["Cuda", "CudaCoop"]



for time, color, label in zip(TIMES, colors, labels):
    plt.plot(dimensions, time, label=label,c=color)
    plt.yscale("log")


slope1, intercept = np.polyfit(np.log(timeSequencial), np.log(dimensions), 1)
slope2, intercept = np.polyfit(np.log(timeCuda), np.log(dimensions), 1)
slope3, intercept = np.polyfit(np.log(timeCudaCoop), np.log(dimensions), 1)

print("Slop Sequencial: ", slope1)
print("Slop Cuda: ", slope2)
print("Slop CudaCoop: ", slope2)

plt.xlabel("Number of Elements in the Matrix")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial vs Cuda vs CudaCoop Row Sum")
plt.legend()
plt.savefig("Sequencial vs Cuda vs CudaCoop Row Sum")
plt.show()

for time, color, label in zip(TIMES2, colors2, labels2):
    plt.plot(dimensions, time, label=label,c=color)

plt.xlabel("Number of Elements in the Matrix")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Cuda vs CudaCoop Row Sum")
plt.legend()
plt.savefig("Cuda vs CudaCoop Row Sum")
plt.show()


plt.clf()
plt.plot(dimensions, timeSequencial, label=labels[0],c=colors[0])
plt.scatter(dimensions, timeSequencial, marker="x",c="black" )

plt.xlabel("Number of Elements in the Matrix")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial Row Sum")
plt.legend()
plt.savefig("Sequencial Row Sum")
plt.show()

plt.clf()
plt.plot(dimensions, timeCuda, label=labels[1],c=colors[1])
plt.scatter(dimensions, timeCuda, marker="x",c="black" )
plt.xlabel("Number of Elements in the Matrix")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Cuda Row Sum")
plt.legend()
plt.savefig("Cuda Row Sum")
plt.show()

plt.clf()
plt.plot(dimensions, timeCudaCoop, label=labels[2],c=colors[2])
plt.scatter(dimensions, timeCudaCoop, marker="x",c="black" )
plt.xlabel("Number of Elements in the Matrix")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("CudaCoop Row Sum")
plt.legend()
plt.savefig("CudaCoop Row Sum")
plt.show()





dimensions = [52451, 562000, 8294400, 24160256]
timeSequencial = [29.433, 253.653 , 3443.6180, 9975.966]
timeCuda = [0.020256, 0.25120, 4.4218, 11.70]
TIMES = [timeSequencial, timeCuda]

colors = ["blue", "red"]
labels = ["Sequencial", "Cuda"]



for time, color, label in zip(TIMES, colors, labels):
    plt.plot(dimensions, time, label=label,c=color)
    plt.yscale("log")

slope1, intercept = np.polyfit(np.log(timeSequencial), np.log(dimensions), 1)
slope2, intercept = np.polyfit(np.log(timeCuda), np.log(dimensions), 1)


print("Slop Sequencial: ", slope1)
print("Slop Cuda: ", slope2)

plt.xlabel("Number of pixels")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial vs Cuda Convolution")
plt.legend()
plt.savefig("Sequencial vs Cuda Convolution")
plt.show()


plt.clf()
plt.plot(dimensions, timeSequencial, label=labels[0],c=colors[0])
plt.scatter(dimensions, timeSequencial, marker="x",c="black" )
plt.xlabel("Number of pixels")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial Blur Image")
plt.legend()
plt.savefig("Sequencial Convolution")
plt.show()


plt.clf()
plt.plot(dimensions, timeCuda, label=labels[1],c=colors[1])
plt.scatter(dimensions, timeCuda, marker="x",c="black" )
plt.xlabel("Number of pixels")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Cuda Convolution")
plt.legend()
plt.savefig("Cuda Convolution")
plt.show()


dimensions = [10000, 100000, 1000000, 10000000, 100000000]
timeSequencial = [0.087, 0.457, 3.412, 34.187, 342.45]
timeCuda = [0.011, 0.015, 0.084, 0.6502, 4.1213]
timeCudaCoop = [0.013, 0.01475, 0.075, 0.60023, 3.2455]

TIMES = [timeSequencial, timeCuda, timeCudaCoop]
TIMES2 = [timeCuda, timeCudaCoop]

colors = ["blue", "red", "green"]
colors2 = ["red", "green"]
labels = ["Sequencial", "CudaNaive", "CudaOptimized"]
labels2 = ["CudaNaive", "CudaOptimized"]



for time, color, label in zip(TIMES, colors, labels):
    plt.plot(dimensions, time, label=label,c=color)
    plt.yscale("log")


slope1, intercept = np.polyfit(np.log(timeSequencial), np.log(dimensions), 1)
slope2, intercept = np.polyfit(np.log(timeCuda), np.log(dimensions), 1)
slope3, intercept = np.polyfit(np.log(timeCudaCoop), np.log(dimensions), 1)

print("Slop Sequencial: ", slope1)
print("Slop Cuda: ", slope2)
print("Slop CudaCoop: ", slope2)

plt.xlabel("Number of Elements in the Array")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial vs CudaNaive vs CudaOptimized Prefix-Sum")
plt.legend()
plt.savefig("Sequencial vs CudaNaive vs CudaOptimized Prefix-Sum")
plt.show()

for time, color, label in zip(TIMES2, colors2, labels2):
    plt.plot(dimensions, time, label=label,c=color)

plt.xlabel("Number of Elements in the Array")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("CudaNaive vs CudaOptimized Prefix-Sum")
plt.legend()
plt.savefig("CudaNaive vs CudaOptimized Prefix-Sum")
plt.show()


plt.clf()
plt.plot(dimensions, timeSequencial, label=labels[0],c=colors[0])
plt.scatter(dimensions, timeSequencial, marker="x",c="black" )

plt.xlabel("Number of Elements in the Array")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial Prefix-Sum")
plt.legend()
plt.savefig("Sequencial Prefix-Sum")
plt.show()

plt.clf()
plt.plot(dimensions, timeCuda, label=labels[1],c=colors[1])
plt.scatter(dimensions, timeCuda, marker="x",c="black" )
plt.xlabel("Number of Elements in the Array")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("CudaNaive Prefix-Sum")
plt.legend()
plt.savefig("CudaNaive Prefix-Sum")
plt.show()

plt.clf()
plt.plot(dimensions, timeCudaCoop, label=labels[2],c=colors[2])
plt.scatter(dimensions, timeCudaCoop, marker="x",c="black" )
plt.xlabel("Number of Elements in the Array")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("CudaOptimized Prefix-Sum")
plt.legend()
plt.savefig("CudaOptimized Prefix-Sum")
plt.show()
