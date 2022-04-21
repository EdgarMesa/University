"""
 Computes and draws a natural spline from a collection of data values
"""

import scipy as sp
import splineutil as su
import matplotlib.pyplot as plt
import numpy.linalg as la


Pts = [(1,3), (4,1), (2,2), (5,8), (1,1), (5,1), (6,1), (6,6), (5,7), (1,8)]
x,y = su.splitPts(Pts)


# Compute the derivatives that will satisfy the natural
# spline conditions
DX = su.Der(x).T[0]
DY = su.Der(y).T[0]

# Use these to compute the list of spline functions
# One for x and one for y
SY = su.splineFunctions(y,DX)
SX = su.splineFunctions(x,DY)

# Plot (X[i](t),Y[i](t)) for t in [0,1]
T = sp.linspace(0,1,200,endpoint=True)

for i in range(len(SY)):
    Q = [SX[i](t) for t in T]
    R = [SY[i](t) for t in T]
    plt.plot(Q, R)

# Plot the points that join the splines
plt.scatter(x, y, 25, color='red')

plt.show()