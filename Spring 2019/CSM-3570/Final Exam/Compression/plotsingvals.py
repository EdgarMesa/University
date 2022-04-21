"""
Skeleton for displaying the singular
values of an image matrix

CSM 3570 Spring 2019

Edgar Mesa Perez
"""

import scipy as sp
import scipy.linalg as la
import matplotlib.pyplot as plt
import matplotlib.image as mpimg

img = mpimg.imread('dog.jpg')
# view overall dimensions
print(img.shape)

# grayscale matrix with only one value per pixel
a = sp.matrix([[x[0] for x in y] for y in img])

# Singular Value Decomposition
u,s, v = la.svd(a)

print(u.shape,  s.shape,v.shape)

# Plot singular values
X = list(range(len(s)))
plt.plot(X, s)
plt.show()

