"""
CSM 3570 - Spring 2019

Illustrate fixed point iteration
"""

import scipy as sp
import math
import rootmethods as rm

f = rm.h(7)
x = 1
for i in range(100):
    print(x)
    x = f(x)

