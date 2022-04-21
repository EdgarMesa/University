"""
Skeleton for jpeg compression computation
for Final Exam

CSM 3570 Spring 2019

Edgar Mesa Perez"""

import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import scipy as sp
import scipy.linalg as la          

img = mpimg.imread('dog.jpg')
gray = sp.matrix([[x[0] for x in y] for y in img])

U, s, V = la.svd(gray)

# set number of singular values to keep and show
# reconstruction of the compressed image
i = 50
compressed =  U[: , :i].dot(sp.diag(s[:i])).dot(V[:i,:])
plt.imshow(gray,cmap='gray')
plt.show()
plt.imshow(compressed,cmap='gray')
plt.show()

# Display the compression ratio
