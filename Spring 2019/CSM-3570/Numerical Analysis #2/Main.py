"""
CSM 3570 -- Spring 2019
Assignment: 2
Submitted by: Edgar Mesa
 """

import math
def firstFunction(x):
    return (1-(1/math.cos(x)))/(math.tan(x)**2)

def secondFunction(x):
    return (1-(1-x)**3)/x

def firstFixed(x):
    return -1/(1+1/math.cos(x))

def secondFixed(x):
    return 3-3*x+x**2

print("a)")
header ="\n{:^17s} {:^17s} {:^17s} {:^17s}\n"
print(header.format("x","E1","E2","E1-E2"))
coeff = 1
for i in range(1,15):
    E1 = firstFunction(coeff)
    E2 = firstFixed(coeff)
    print("{:^17.14f} {:^17.14f} {:^17.14f} {:^17.14f}".format(coeff, E1, E2, abs(E1-E2)))
    coeff = coeff /10

print()
print()
print("b)")
header ="\n{:^17s} {:^17s} {:^17s} {:^17s}\n"
print(header.format("x","E1","E2","E1-E2"))
coeff = 1
for i in range(1,15):
    E1 = secondFunction(coeff)
    E2 = secondFixed(coeff)
    print("{:^17.14f} {:^17.14f} {:^17.14f} {:^17.14f}".format(coeff, E1, E2, abs(E1-E2)))
    coeff = coeff/10




