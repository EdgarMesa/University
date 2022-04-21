import numpy as np
import math
from itertools import permutations

"""This function takes the cipher v as a decimal number(A long decimal number, the result of all 1s and 0s
put together for the variable cipher in this case)"""


def substitution(v, l, padding):
    global s_inverse
    # This will change a number 123456 to its binary 11110001001000000 with the right padding that we are using, for number 3 is 294
    binario = str(format(int(bin(v)[2:]), "#0" + str(padding)))
    # make the block of size l and turn each block to binary
    blocks = [int(binario[i:i + l], 2) for i in range(0, len(binario), l)]
    print("v broken into blocks")
    print(blocks)
    blocks = np.array(blocks)
    # Apply the substitution
    for i in range(len(blocks)):
        blocks[i] = s_inverse[blocks[i]]
    # Turn those decimal to blocks to binary
    binario = [format(int(bin(x)[2:]), "#0" + str(l)) for x in blocks]
    print("After substitution")
    print(binario, "\n")
    # Flaten all those blocks, putting all the binary numbers together
    bina = ""
    for b in binario:
        bina += str(b)
    # decimal number of that long binary is returned
    bina = int(bina, 2)

    return bina


""" function that gives you s inverse. No need to rest one from all,
 the resulted array will be of the right len"""

def inverse_sub(list):
    result = [None] * len(list)
    for i in range(len(list)):
        index = list[i]
        result[index] = i
    return result

""" function to apply the permutation, takes a long decimal as the substitution """
def permutation(state, padding):
    global p_inverse
    # long binary cipher from the long decimal
    bina = str(format(int(bin(state)[2:]), "#0" + str(padding)))
    result = [0] * len(bina)
    # Applying the permutation
    for i in range(len(bina)):
        result[i] = bina[p_inverse[i]]

    # putting all the binaries together and returning the de decimal
    result = "".join(str(x) for x in result)
    print("After permutation")
    print(result, "\n")
    result = int(result, 2)

    return result


""" function that gives you p inverse"""
def inversePermutation(arr):
    size = len(arr)

    # To store element to index mappings
    arr2 = [0] * (size)

    # Inserting position at their
    # respective element in second array
    for i in range(0, size):
        arr2[arr[i]] = i

    result = arr2.copy()
    return result

def addOne(key):
    key1 = ""
    for i in range(len(key) - 1 , 0, -1):
        if key[i] == '0':
            key1 = key[:-(len(key) - i)]
            key1 = key1 + "1"
            for j in range(len(key) - i - 1):
                key1 = key1 + "0"
            return key1

def sliceKey(key):
    key1 = key[:-32]
    key2 = key[16:-16]
    key3 = key[32:]
    keys = [key1, key2, key3]
    return keys

if __name__ == "__main__":
    for i in range(10):
        if i == 9:
            i = 0
        print(i)