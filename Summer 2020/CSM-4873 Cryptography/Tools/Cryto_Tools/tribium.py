from pytrivium import Trivium
import numpy as np
# Set 6, vector# 3:

def to_hex(stin):
    stin = stin.split(sep=",")
    result = []
    for s in stin:
        result.append(int(s, 16))
    return np.array(result)

def key_generator(arr):
    final_ex = ""
    for i in range(20):

        h = arr[i]
        hexx = hex(h)

        final_ex = final_ex + hex(h)[2:]

    final_ex  = [int(final_ex[i:i + 2], 16) for i in range(0, len(final_ex), 2)]

    return final_ex


cipher  = to_hex("5D,6B,F1,E9,A9,E4,4C,40,45,48,56,88,6B,60,33,F4,EC,D4,61,BE,A1,B9,7E,D0,63,51,E6,C2,02,97,4A,32,01,99,2F,29,46,BD,8F,78,74,BD,8D")


for i in range(256):
    for j in range(256):
        key, iv = [i] * 10, [j] * 10
        print("Key: ", key, "IV: ", iv)

        engine = Trivium()
        engine.initialize(key, iv)

        engine.update(20)
        a = engine.finalize()
        a = key_generator(a)




        streamkey = np.array(a[:len(cipher)])
        print("ci:", cipher)
        print("stream: ", streamkey)
        plain = np.bitwise_xor(cipher, streamkey)
        #print(plain)
        plain = "".join(chr(x) for x in plain)

        print("Plaintex: ", plain)
        print()
        if "cryptoFlag{" in plain:
            print("Cipher: ", cipher)
            exit()




