"""
CSM 3570 - Spring 2019

Illustrates cobweb diagrams
"""

import numpy as np
import matplotlib.pyplot as plt
import rootmethods as rm
import math

# Golden Mean
gm = (1+np.sqrt(5))/2

def p(x):
   return np.cos(x)


sq = rm.cubic(2)


# set the function
f = sq

# Left and right endpoints of the plot
#a, b = 0, 1
#a, b = -gm, gm
a, b = 0, 3

# Plot the function
X = np.linspace(a, b, 200, endpoint=True)
Y = [f(x) for x in X]
plt.plot(X,Y)

# Plot the  x-axis
plt.plot([X[0],X[-1]], [0,0])

#Plot the y-axis
plt.plot([1,1],[min(Y), max(Y)])

# Plot the line y=x
plt.plot(X,X)

# Build a list of points on the cobweb
seed = 1/2
steps = 400

L = rm.cobweb(f, seed, steps)

# Create a list of the x- and y-coordinates of these points
LX, LY = [P[0] for P in L], [P[1] for P in L]
# Plot the cobweb
plt.plot(LX, LY)

# Put a green dot on the first point and a red dot on the last point
plt.scatter([LX[0],LX[-1]], [LY[0], LY[-1]])

plt.show()

