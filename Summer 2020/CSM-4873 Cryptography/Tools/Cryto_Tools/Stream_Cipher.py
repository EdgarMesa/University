
import numpy as np


def binary_permutation(number_bits, arr, i, result):
    if i == number_bits:
        result.append(np.array(arr.copy() * 60))
        return

        # First assign "0" at ith position
        # and try for all other permutations
        # for remaining positions
    arr[i] = 0
    binary_permutation(number_bits, arr, i + 1, result)

    # And then assign "1" at ith position
    # and try for all other permutations
    # for remaining positions
    arr[i] = 1
    binary_permutation(number_bits, arr, i + 1, result)

def key_generator(len, C_list):
    key = C_list.copy()

    for i in range(len):

        new = (key[i + 6] + key[i + 5] + key[i + 4] + key[i + 3] + key[i + 2] + key[i + 1] + key[i]) % 2
        key.append(new)

    return key

if __name__=="__main__":
    cippher = [1,1,0,1,0,0,0,0,1,0,0,1,1,0,1,0,1,1,0,1,1,0,0,0,1,0,0,0,0,0,0,1,0,1,1,0,1,1,0,1,0,1,0,1,0,1,1,1,0,1,1,1,0,0,0,1,1,1,1,0,1,0,0,0,1,0,1,1,0,0,0,1,0,1,1,1,1,1,1,1,0,1,1,0,0,0,1,0,0,0,0,0,0,1,1,0,1,0,1,1,1,1,0,1,0,0,0,0,1,0,1,0,1,1,1,0,1,0,0,1,1,0,0,0,0,1,0,1,1,1,0,0,0,1,1,1,0,1,0,0,1,0,0,1,0,1,0,0,0,1,1,0,0,1,0,0,0,1,0,0,1,0,1,1,1,1,1,1,1,0,0,1,0,0,0,1,1,0,1,0,0,1,1,1,1,0,1,0,0,0,1,1,1,0,1,1,0,1,0,0,1,1,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,1,0,0,1,1,0,0,1,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,1,1,1,0,0,1,0,0,1,0,0,0,1,1,0,1,1,0]

    cipher = np.array(cippher)

    final_ci = []
    ci = [final_ci.append(cipher[i:i + 7]) for i in range(0, len(cipher), 7)]
    limpio = []
    for i in final_ci:
        res = 0
        for ele in i:
            res = (res << 1) | ele
        limpio.append(res)
    limpio_plain = np.array(limpio)

    number_bist = 10
    ar = [None] * number_bist
    result = []
    binary_permutation(number_bist, ar, 0, result)
    for permu in result:
        final_ci = []
        ci = [final_ci.append(permu[i:i + 7]) for i in range(0, len(permu), 7)]
        limpio = []
        for i in final_ci:

            res = 0
            for ele in i:
                res = (res << 1) | ele
            limpio.append(res)

        limpio = limpio[:len(limpio_plain)]

        plain = np.bitwise_xor(limpio, limpio_plain)
        #print(plain)
        plain = "".join(chr(x) for x in plain)

        if "cry" in plain:
            print("Plaintex: ", plain)

            break

