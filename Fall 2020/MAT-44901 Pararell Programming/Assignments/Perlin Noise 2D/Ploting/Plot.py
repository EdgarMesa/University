from contextlib import nullcontext
import numpy as np
import matplotlib.pyplot as plt

colors = ["blue", "green", "red", "cyan", "magenta", "yellow"]
models = ["Row Stride", "Block Stride", "Pixel Stride", "Next Free Row", "Next Free Pixel", "Next Free Block"]

row_stride = np.array([12.648313,10.080641,5.994957,4.382424,4.275933,4.150908,4.099065,3.163817,3.125047,3.270863,3.237120,4.026171,4.187317,4.153684,4.116925,3.980828,3.050879,3.656399,3.508667,3.274274,3.190164,3.280716,3.399852,3.745037,3.704008,3.831731,3.761277,3.621712,3.535227,3.479516,3.525116])
pixel_stride = np.array([11.098646,7.614373,6.929295,4.891215,4.407855,4.599983,4.105132,3.794615,3.601884,3.572791,3.611273,4.528460,4.388022,4.331130,4.190562,4.043949,3.961645,3.682721,3.596077,3.408477,3.332439,3.325370,3.393105,3.809488,3.769584,3.751315,3.794083,3.907491,3.915472,3.987419,3.827941])
block_stride = np.array([10.774735,7.551594,5.749155,4.734733,4.455114,4.460783,3.923309,3.717116,3.288990,3.190220,3.410204,4.143691,4.033818,3.920783,4.054933,3.864521,3.675890,3.550107,3.391773,3.405510,3.272750,3.235127,3.194733,3.654885,3.562065,3.503862,3.516538,3.514389,3.495425,3.543500,3.382314])
next_freeRow = np.array([10.907911,7.563795,5.731793,4.963324,4.432314,4.021527,3.845499,3.756289,3.341464,3.217828,3.346546,3.149339,3.234059,3.219522,3.453849,3.206112,3.786626,3.173174,3.544601,3.414959,3.382217,3.442871,3.440293,3.439390,3.406931,3.395455,3.168588,3.435504,3.305595,3.480576,3.648172])
next_freePixel = np.array([154.971446,162.526926,165.984336,165.883181,166.811936,120.598810,120.358536,168.078807,166.966038,165.920755,119.957279,119.420916,119.836465,166.439318,167.139557,165.463010,167.151841,167.246794,169.725316,168.025592,168.727059,167.529517,167.267193,169.755025,204.440551,243.100700,166.926699,120.030098,166.197336,120.104955,167.381603])
next_freeBlock = np.array([10.852808,7.474998,5.755477,4.713843,4.185432,4.109076,3.843492,3.480225,3.626326,3.452871,3.456329,3.127177,3.519738,3.335411,3.063952,3.250261,3.300690,3.246855,3.212555,3.052661,3.022781,3.157675,3.276897,3.097759,3.184300,3.006570,3.200457,3.072478,3.241967,3.300780,3.347123])
single_thread = np.array([22.835707])


next_free_block_row = [3, 5]
best_ones = [0, 1, 2, 3, 5]
data = [row_stride, pixel_stride, block_stride, next_freeRow, next_freePixel, next_freeBlock]


number_threads = np.arange(2, 33)

for i in range(3):
        plt.plot(number_threads, data[i], c=colors[i], label=models[i])
        plt.title("Comparation Between Stride Models")
        plt.xlabel("Number of Threads")
        plt.ylabel("Time in seconds")   

plt.legend()
plt.savefig("Comparation Between Stride Models")
plt.show()
plt.clf()

for i in next_free_block_row:
        plt.plot(number_threads, data[i], c=colors[i], label=models[i])
        plt.title("Next Free Row vs Next Free Block")
        plt.xlabel("Number of Threads")
        plt.ylabel("Time in seconds")   

plt.legend()
plt.savefig("Next Free Row vs Next Free Block")
plt.show()
plt.clf()


for i in best_ones:
        plt.plot(number_threads, data[i], c=colors[i], label=models[i])
        plt.title("Comparation Best Models")
        plt.xlabel("Number of Threads")
        plt.ylabel("Time in seconds")   

plt.legend()
plt.savefig("Comparation Best Models")
plt.show()
plt.clf()


for i in range(3, 6):
        plt.plot(number_threads, data[i], c=colors[i], label=models[i])
        plt.title("Comparation Between Next Free Models")
        plt.xlabel("Number of Threads")
        plt.ylabel("Time in seconds")   

plt.legend()
plt.savefig("Comparation Between Next Free Models")
plt.show()
plt.clf()


for times, model, color in zip(data, models, colors):
    plt.title("Speed\\Number of Threads " + model+ " Model")
    plt.xlabel("Number of Threads")
    plt.ylabel("Time in seconds")
    plt.plot(number_threads, times, c=color, label=model)
    plt.savefig(model + " Model")
    plt.show()
    plt.clf()

plt.title("Speed\\Number of Threads")
plt.xlabel("Number of Threads")
plt.ylabel("Time in seconds")

for times, model, color in zip(data, models, colors):
    plt.plot(number_threads, times, c=color, label=model)
    plt.savefig("Comparation Between Models")

plt.tight_layout()
plt.legend()
plt.show()
