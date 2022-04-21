"""
CSM 3570 - Spring 2019

Emulate the table on Sauer page 28
for the bisection method
"""

import scipy as sp
import rootmethods as rm


def p(x):
    return x**3 + x - 1

# for 3 decimal places, tolerance should be 0.0005
rm.displaybisect(p,0,1,.0005)

