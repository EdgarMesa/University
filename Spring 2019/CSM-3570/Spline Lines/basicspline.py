"""
First attempt at cubic spline computation.

Given [y0,..,ym] and [D0,..,Dm] produce the sequence of
4-tuples that determine the spline and then
draw that spline interpolating [(i,yi) for i in range(m)]

"""

import scipy as sp
import matplotlib.pyplot as plt
import math

def nest1(coeff):
    """
    Returns a polynomial function evalutated using Horner's Method
    Args
        coeff: list of coefficients in decreasing order of degree

    Returns
        Polynomial function defined by these coefficients
    """

    def f(x):
        deg = len(coeff) - 1
        result = coeff[-1]
        for i in range(deg - 1, -1, -1):
            result = result * x + coeff[i]

        return result

    return f

def cubic (coeff):
    """
    Returns the cubic polynomial function whose coefficients are coeff
    Args:
        coeff: the list [a,b,c,d] of the coefficients of a+bt+ct^2+dt^3
    Returns:
        the cubic polynomial -- as a function -- determined by these coefficients
    """
    if len(coeff) != 4:
        return
    return nest1(coeff)

def splineCoefficients(y,D):
    """
    Computes the coefficients for the spline cubics defined by the
    values y and the derivatives D -- each as a row vector
    Args:
        y: the values being connected
        D: the values of the derivatives at these points
    Return:
         List of the 4 coefficients for each cubic spline
    """
    return [[y[i],D[i],3*(y[i+1]-y[i])-2*D[i] - D[i+1],-2*(y[i+1]-y[i])+D[i]+D[i+1]]
            for i in range(len(y)-1)]

def splineFunctions(y,D):
    """
    Computes the list of the actual cubic spline functions
    Args:
        y: The points joining the splines
        D: The derivatives at those points
    Return:
         List of the cubic functions defined this data
    """
    return [cubic(c) for c in splineCoefficients(y,D)]

# y values
Ptx = sp.linspace(0, math.pi*6, 13)
y = [math.sin(x) for x in Ptx]

plt.scatter(Ptx,y)


# derivative values
D = [ math.cos(x) for x in Ptx]

# Build piecewise functions
Y = splineFunctions(y, D)

# Plot Yi on [i,i+1]
T = sp.linspace(0, 1, 100)

for i in range(len(y)-1):
    X = [math.pi/2*(t+i) for t in T]
    Z = [Y[i](t) for t in T]
    plt.plot(X,Z)

plt.show()
