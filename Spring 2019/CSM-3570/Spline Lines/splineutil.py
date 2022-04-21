"""
Utility functions for computing natural cubic splines

Peter Andrews for CSM 3570 Spring 2019
"""
import scipy as sp
import scipy.linalg as la

def splitPts(Plist):
    return [P[0] for P in Plist], [P[1] for P in Plist]

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



def A(n):
    """
    Computes the left hand side of the  (n) x (n) system
    for determining the natural spline derivatives

    Args:
        n: number of points for the spline curve
    Returns:
        The LHS matrix -- see cubic spline notes
    """

    z = [0 for i in range(n)]
    top = [2,1] + z[2:]
    bottom = z[:n-2] + [1,2]
    mid = [1,4,1]

    H = [top]
    H = H + [z[:i] + mid + z[i+3:] for i in range(n-2)]
    H = H + [bottom]

    return sp.array(H)


def B(y):
    """
    returns the target for the derivative computation

    Args:
        y: list of values to be interpolated
    Returns:
        list of the entries in the target vector
        as an sp.array of shape (len(y),1)
    """

    # Needs to be completed as Exercise 4
    length = len(y)
    top = [[3*(y[1]-y[0])]]
    botom = [[3*(y[length-1]-y[length-2])]]
    middle = [[3*(y[i]-y[i-2])] for i in range(2,length)]
    array = top+middle+botom
    a = sp.array(array)
    return a


def Der(y):
    """
    Finishes the solution of the derivative vector
    back substitution from U

    Args:
        y: vector of given values
    Returns:
        Solution to A(len(y)) x = Beta(y) as
        an sp.array of shape (1,len(y))
    """

    #  This needs to be completed as Exercise 5


    return la.solve(A(len(y)),B(y))



