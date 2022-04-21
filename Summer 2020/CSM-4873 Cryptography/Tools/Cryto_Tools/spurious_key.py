
from pycipher import Affine



def get_words_length(lenght):
    global dict
    words = []
    for w in dict:
        if len(w) == lenght:

            words.append(w)

    return words

def is_words(word):
    global dict
    words = []
    for w in dict:
        if word in w:
            words.append(w)
    postive_result = True if len(words) != 0 else False
    return postive_result, words

def to_char(list_numbers):
    chars = []
    for n in list_numbers:
        chars.append(chr(n + 65))
    return chars

def to_numers(list_chars):
    nums = []
    for c in list_chars:
        nums.append(ord(c) - 65)
    return nums



if __name__=="__main__":

    file = open("words2.txt", 'r')
    dict = file.read().split(sep="\n")
    inverses_26Z = [1, 3, 5, 7, 11, 17, 25]
    size = 8

    fixed_len = get_words_length(size)
    fixed_len.sort()

    #ret, words_containing = is_words("AA")
    cipher_seen = {}
    for word in fixed_len:
        for a in inverses_26Z:
            for b in range(26):
                if a == 1 and b == 0:
                    continue
                ciphertext = Affine(a, b).encipher(word)

                if ciphertext in cipher_seen:
                    print("Spurious Key: ", a, " and ", b, " Ambiguous Ciphertext: ", ciphertext, " Incorrect Decryption: ", word, " Correct Plaintext: ", cipher_seen[ciphertext][0], " Correct Key: ", cipher_seen[ciphertext][1]," ", cipher_seen[ciphertext][2])
                    exit()
                else:
                    cipher_seen[ciphertext] = [word, a, b]
    print("NO SPURIOUS KEY FOR LEN!: ", size)




    


