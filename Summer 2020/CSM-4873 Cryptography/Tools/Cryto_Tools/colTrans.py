from pycipher import ColTrans
from itertools import permutations
import numpy as np



a = [1,0,1,0,1,0]
p = "101010"

for i in range(len(a)):
    if a[i] == ord(p[i]) - 48:
        print("SAMEEEEE", p[i])

