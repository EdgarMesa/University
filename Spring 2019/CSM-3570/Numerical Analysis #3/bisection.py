"""
CSM 3570 - Spring 2019

Bisection Method for root finding
"""

import math
import rootmethods as rm

def rootlocator(f, a, b, delta):
    x= a
    while x < b:
        print("{:4.2f}  {:6.3f}".format(x,f(x)))
        x += delta


def f(x):
    return x**5+x-1

rootlocator(f, 0, 1, 1/8)

r = rm.bisect(f, 0.5, 0.75, .00000005)
print("The root is approximately r = {:10.7}".format(r))
print("Evaluating f at this approximate root -- {:10.7f}".format(f(r)))
print()
print(f(0.5))
