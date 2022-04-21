import numpy as np
import math

"""This function takes the cipher v as a decimal number(A long decimal number, the result of all 1s and 0s
put together for the variable cipher in this case)"""


def substitution(v, l, padding):
    global s_inverse
    # This will change a number 123456 to its binary 11110001001000000 with the right padding that we are using, for number 3 is 294
    binario = str(format(int(bin(v)[2:]), "#0" + str(padding)))
    # make the block of size l and turn each block to binary
    blocks = [int(binario[i:i + l], 2) for i in range(0, len(binario), l)]
    #print("v broken into blocks")
    #print(blocks)
    blocks = np.array(blocks)
    # Apply the substitution
    for i in range(len(blocks)):
        blocks[i] = s_inverse[blocks[i]]
    # Turn those decimal to blocks to binary
    binario = [format(int(bin(x)[2:]), "#0" + str(l)) for x in blocks]
    #print("After substitution")
    #print(binario, "\n")
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
    #print("After permutation")
    #print(result, "\n")
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
    plaintext = []
    ciphertext = []
    with open("spn.dat", "r") as f:
        plaintext_line = f.readline()
        ciphertext_line = f.readline()
        while ciphertext_line:
            plaintext_line = plaintext_line.rstrip()
            plaintext_line = plaintext_line.split("\t")
            plaintext_line = "".join(plaintext_line)
            plaintext.append(plaintext_line)
            ciphertext_line = ciphertext_line.rstrip()
            ciphertext_line = ciphertext_line.split("\t")
            ciphertext_line = "".join(ciphertext_line)
            ciphertext.append(ciphertext_line)

            plaintext_line = f.readline()
            ciphertext_line = f.readline()

    key = "000000000000000100000000000000000000000000000000"
    s = [15,5,14,2,3,16,12,9,4,11,7,13,6,10,1,8]
    p = [1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16]

    p = [x - 1 for x in p]
    s = [x - 1 for x in s]
    padding = 16
    keystream = sliceKey(key)
    p_inverse = p
    p_inverse = np.array(p_inverse)
    s_inverse = np.array(s)
    l = int(math.log(len(s), 2))
    m = len(p) // l
    i = 0
    plaintext = plaintext[:12]
    ciphertext = ciphertext[:12]
    while i < len(plaintext):
        current_pt = plaintext[i]
        current_ct = ciphertext[i]


        current_pt = int(current_pt, 2)
        # begin the encryption
        v = current_pt ^ int(keystream[0], 2)   # first xor
        v = substitution(v, l, padding)   #goes into subs
        state = permutation(v, padding) # we do perm
        v = state ^int(keystream[1], 2)       #second xor with the perm
        v = substitution(v, l, padding)   # second subs
        v = v ^ int(keystream[2], 2)       # final XOR
        print(keystream)

        if current_ct is not v:
            key = addOne(key)
            keystream = sliceKey(key)
            i = 0
            continue
        else:
            i += 1

        print("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT")

    print("KEYSTREAM: ", keystream)
    keystream = [int(x, 2) for x in keystream]
    print(sum(keystream))