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


if __name__ == "__main__":
    p = [259,64,60,190,286,242,231,100,132,130,116,271,43,241,29,17,70,237,141,278,201,11,151,44,121,181,87,210,154,208,254,122,257,294,252,152,256,218,221,107,108,46,76,67,285,283,196,203,277,155,28,32,134,204,49,48,86,182,198,150,227,165,163,104,127,149,243,71,61,106,265,101,280,156,23,138,59,78,274,185,129,207,153,215,219,273,245,84,212,25,250,117,135,251,288,126,40,255,79,97,27,94,14,270,172,232,13,92,159,24,82,179,36,35,5,191,75,161,143,199,284,238,109,22,272,136,291,184,169,177,229,275,174,264,173,45,246,267,178,26,12,164,160,118,131,124,8,6,3,223,205,248,112,31,147,157,72,188,39,16,2,206,133,33,158,37,145,211,209,142,240,98,62,119,282,260,30,63,176,225,202,93,125,38,58,293,213,4,183,233,85,137,171,279,111,236,52,197,180,224,266,113,73,220,65,192,186,170,88,42,51,77,91,95,41,263,55,262,89,144,162,268,168,34,216,290,226,195,139,166,47,69,228,281,249,81,187,102,66,90,105,9,193,1,120,292,10,276,7,68,15,235,114,234,74,175,99,214,50,21,253,258,222,57,244,261,103,96,18,217,239,123,167,146,54,287,140,115,194,56,200,189,289,148,19,110,80,230,269,247,83,53,128,20]
    # decreasing by one all p for the permutation to work here
    p = [x - 1 for x in p]

    key_stream = [18380755966219046142836776327637176737933385647979133274669389540643637416107994148398784,26533892012152156897201919270736554499910269553541652856713602937939018690104645205426112,19946992129876638680381043480062568679162472234451078280067754507069374226943211908633898,22686009125666775275801530803908973963989549352445083899745204896615044122676330262648971,8242553753603315281766120621108139945826318732425397097132965132058807552584004563327982]
    s = [46,63,27,5,21,30,20,2,32,52,54,23,48,6,36,50,29,64,3,62,22,35,41,55,60,43,24,12,13,25,57,56,61,28,31,14,47,9,49,7,18,53,34,33,19,58,8,59,15,38,44,10,37,39,45,40,42,17,4,51,26,1,11,16]
    s = [x - 1 for x in s]
    cipher = [0,0,0,1,1,1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,1,1,0,0,1,1,1,1,0,1,1,1,1,1,0,1,1,1,0,0,0,0,0,0,1,1,0,1,1,1,0,1,1,1,1,1,0,0,1,1,1,1,0,0,0,1,1,1,0,1,0,1,1,1,1,1,0,1,1,0,1,0,0,1,1,0,0,1,0,1,1,0,1,0,0,0,0,1,1,1,0,0,0,0,1,1,0,1,1,0,1,0,0,0,1,0,1,1,1,0,1,1,1,0,1,1,0,0,0,1,0,0,1,1,0,0,1,0,0,1,0,1,1,1,1,1,1,1,0,0,1,1,0,1,0,0,1,1,1,0,0,0,0,1,0,0,1,0,1,1,0,1,1,0,1,1,0,0,0,1,0,0,1,0,0,0,0,0,1,0,1,0,1,1,0,0,0,1,0,1,1,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,1,1,1,0,1,1,0,0,1,1,0,0,1,1,1,0,1,0,0,0,0,0,1,0,1,0,0,1,1,0,1,0,0,0,0,0,0,1,0,0,1,1,0,0,0,1,0,1,0,1,0,1,1,0,0,0,1,0,0,0,1,1,0,0,0,1,1,1,0,1,0]
    padding = 294

    key_stream.reverse()

    p_inverse = inversePermutation(p)
    p_inverse = np.array(p_inverse)
    s_inverse = np.array(inverse_sub(s))
    cipher = np.array(cipher)
    cipher = "".join([str(x) for x in cipher])
    cipher = int(cipher, 2)

    print("Cipher: ", cipher)
    l = int(math.log(len(s), 2))
    m = len(p) // l

    print("Inverse p: ", p_inverse)
    print("Inverse s: ", s_inverse)
    print("l: ", l)
    print("m: ", m)

    # Applying the decription step by step as in the diagram in the slides, this can be automated
    v = cipher ^ key_stream[0]
    v = substitution(v, l, padding)
    v = v ^ key_stream[1]

    state = permutation(v, padding)
    v = substitution(state, l, padding)
    v = v ^ key_stream[2]

    state = permutation(v, padding)
    v = substitution(state, l, padding)
    v = v ^ key_stream[3]

    state = permutation(v, padding)
    v = substitution(state, l, padding)
    v = v ^ key_stream[4]

    v = str(format(int(bin(v)[2:]), "#0294"))
    v = [int(v[i:i + 7], 2) for i in range(0, len(v), 7)]
    print("Flag: " + "".join(chr(x) for x in v))
