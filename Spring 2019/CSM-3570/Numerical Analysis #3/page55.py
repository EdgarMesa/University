"""
CSM 3570 - Spring 2019

Newton's Method
Replicates the table on page 55
"""
import rootmethods as rm
import math

def f(x):
    return (10*math.pi*x**2)+((4/3)*math.pi*x**3)-400

def fprime(x):
    return (10*math.pi*2*x)+((4/3)*math.pi*2*x**2)

r = rm.newton(f, fprime, 3.10, 10)
rm.displaynewton(f, fprime, 3.10, 10, r)
print("\nr: {:>15.10}, f(r): {:>15.10}".format(r, f(r)))

