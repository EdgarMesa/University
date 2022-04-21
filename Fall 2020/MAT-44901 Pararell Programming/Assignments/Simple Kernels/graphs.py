
import numpy as np
import matplotlib.pyplot as plt
import numpy


def slope(x1, y1, x2, y2):
    return (y2-y1)/(x2-x1)

dimensions = numpy.arange(500,3001,500)
dimensions = [x * x for x in dimensions]
timeSequencial = np.array([1241.512, 9893.59, 45105.7, 138994.93, 277931.808, 493593.203])
timeCuda = np.array([0.342, 1.3773, 7.9658, 18.519, 45.773, 82.113])
tilted = np.array([0.115866, 1.1839, 6.8151, 15.927, 32.121, 64.469])
TIMES = [timeSequencial, timeCuda, tilted]

colors = ["blue", "red", "green"]
labels = ["Sequencial", "Cuda", "Tilted cuda"]


for time, color, label in zip(TIMES, colors, labels):
    plt.plot(dimensions, time, label=label,c=color)
    plt.yscale("log")


slope1, intercept = np.polyfit(np.log(timeSequencial), np.log(dimensions), 1)
slope2, intercept = np.polyfit(np.log(timeCuda), np.log(dimensions), 1)
slope3, intercept = np.polyfit(np.log(tilted), np.log(dimensions), 1)



print("Slop Sequencial: ", slope1)
print("Slop Cuda: ", slope2)
print("Slop Tilted: ", slope3)

plt.xlabel("Total number of elements")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial vs Pararell vs Tilted Matrix Multiplication")
plt.legend()
plt.savefig("Sequencial vs Pararell vs Tilted Matrix Multiplication")
plt.show()


plt.clf()
plt.plot(dimensions, timeSequencial, label=labels[0],c=colors[0])
plt.xlabel("Total number of elements")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial Matrix Multiplication")
plt.legend()
plt.savefig("Sequencial Matrix Multiplication")
plt.show()

plt.clf()
plt.plot(dimensions, timeCuda, label=labels[1],c=colors[1])
plt.xlabel("Total number of elements")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Cuda Matrix Multiplication")
plt.legend()
plt.savefig("Cuda Matrix Multiplication")
plt.show()

plt.clf()
plt.plot(dimensions, timeCuda, label=labels[2],c=colors[2])
plt.xlabel("Total number of elements")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Tilted Matrix Multiplication")
plt.legend()
plt.savefig("Tilted Matrix Multiplication")
plt.show()




dimensions = [100, 200, 300, 400, 500, 600, 700]
timeSequencial = [3605.486, 7332.527, 10789.939, 14791.953, 19365.514, 22502.37,  26142.048]
timeTilted = [0.43312, 1.3106, 1.9876, 2.6374, 3.2961, 3.9527, 4.8227]
TIMES = [timeSequencial, timeTilted]

colors = ["blue", "red"]
labels = ["Sequencial", "Tilted"]



for time, color, label in zip(TIMES, colors, labels):
    plt.plot(dimensions, time, label=label,c=color)
    plt.yscale("log")


slope1, intercept = np.polyfit(np.log(timeSequencial), np.log(dimensions), 1)
slope2, intercept = np.polyfit(np.log(timeTilted), np.log(dimensions), 1)

print("Slop Sequencial: ", slope1)
print("Slop Tilted: ", slope2)

plt.xlabel("Number of Images Averaged")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial vs Tilted Images Average")
plt.legend()
plt.savefig("Sequencial vs Images Average Images Average")
plt.show()


plt.clf()
plt.plot(dimensions, timeSequencial, label=labels[0],c=colors[0])
plt.xlabel("Number of Images Averaged")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Sequencial Images Average")
plt.legend()
plt.savefig("Sequencial Images Average")
plt.show()

plt.clf()
plt.plot(dimensions, timeTilted, label=labels[1],c=colors[1])
plt.xlabel("Number of Images Averaged")
plt.ylabel("Time In Miliseconds (ms)")
plt.title("Tilted Images Average")
plt.legend()
plt.savefig("Tilted Images Average")
plt.show()




# dimensions = [10000, 221841, 921600, 8294400, 24160256]
# timeSequencial = [20.7310, 360.06 ,1448.5940, 12971.229, 37876.2760]
# timeCuda = [0.043328, 0.14214, 1.5380, 12.814, 26.427]
# TIMES = [timeSequencial, timeCuda]



# for time, color, label in zip(TIMES, colors, labels):
#     plt.plot(dimensions, time, label=label,c=color)

# plt.xlabel("Pixels of the image")
# plt.ylabel("Time In Miliseconds (ms)")
# plt.title("Sequencial vs Pararell Blur Image")
# plt.legend()
# plt.savefig("Sequencial vs Pararell Blur Image")
# plt.show()


# plt.clf()
# plt.plot(dimensions, timeSequencial, label=labels[0],c=colors[0])
# plt.xlabel("Pixels of the image")
# plt.ylabel("Time In Miliseconds (ms)")
# plt.title("Sequencial Blur Image")
# plt.legend()
# plt.savefig("Sequencial Blur Image")
# plt.show()


# plt.clf()
# plt.plot(dimensions, timeCuda, label=labels[1],c=colors[1])
# plt.xlabel("Pixels of the image")
# plt.ylabel("Time In Miliseconds (ms)")
# plt.title("Pararell Blur Image")
# plt.legend()
# plt.savefig("Pararell Blur Image")
# plt.show()
