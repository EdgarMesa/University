"""
CSM 3570 - Spring 2019

Programs from Sauer Chapter 1
"""
import scipy as sp
import math
import numpy as np
import matplotlib.pyplot as plt



def bisect(f, a, b, tol):
    """
    see Program 1.1 on pages 28-29

    Args:
        f: function whose roots we are seeking
        a: left end of initial interval
        b: right end of initial interval
        tol: stop when error is less than this

    Return: Approximation to a root of f in (a,b)
    """

    fa = f(a)
    fb = f(b)
    if sp.sign(fa) * sp.sign(fb) >= 0:
        print('f(a)f(b)<0 not satisfied!')
        return
    while (b - a) / 2.0 > tol:
        c = (a + b) / 2.0
        fc = f(c)
        if fc == 0:  # c is a solution, done
            return c
        if sp.sign(fc) * sp.sign(fa) < 0:  # a and c make the new interval
            b = c
            fb = fc
        else:                               # c and b make the new interval
            a = c
            fa = fc
    return (a + b) / 2.0                    # new midpoint is best estimate


def displaybisect(f, a, b, tolerance):
    """
    see Program 1.1 on pages 28-29
    displays to the console each step of the
    computation

    Args:
        f: function whose roots we are seeking
        a: left end of initial interval
        b: right end of initial interval
        tol: stop when error is less than this

    Return: Approximation to a root of f in (a,b)
    """
    # Set up printing format
    headerfmt = "\n{:^3s} {:^7s} {:^5s} {:^7s} {:^5s} {:^7s} {:^5s}\n"
    entryfmt = "{:^3d} {:7.4f} {:^+5d} {:7.4f} {:^+5d} {:7.4f} {:^+5d}"

    # Print column titles
    print(headerfmt.format("i",
                           "a", "f(a)", "c", "f(c)", "b", "f(b)"))

    # Successive approximations
    n = 0
    while (b-a)/2 > tolerance:
        c = (a+b)/2
        print(entryfmt.format
              (n, a, int(sp.sign(f(a))),
               c, int(sp.sign(f(c))),
               b, int(sp.sign(f(b)))))
        n += 1
        if f(c) == 0:
            return c
        if f(a)*f(c) < 0:
            b = c
        else:
            a = c
    return (a+b)/2




def newton(f, fp, x0, n):
    """
    Newton's method with initial point x0 and n iterations

    Args:
        f: function
        fp: derivative
        x0: initial "guess"
        n: number of iterations
    Returns:
        approximation to the root r -- f(r) = 0
    """

    def nextIterate(x):
        return x - f(x) / fp(x)

    current = x0
    for i in range(n):
        next = nextIterate(current)
        current = next

    return current


def displaynewton(f, fp, x0, n, r):
    """
    Newwton's method with initial point x0 and n iterations
    Prints out results at each step

    Args:
        f: function
        fp: derivative
        x0: initial "guess"
        n: number of iterations
    Returns:
        approximation to the root r -- f(r) = 0
    """

    def nextIterate(x):
        return x - f(x) / fp(x)

    # Set up formats
    label_fmtstr = "\n{:^3s}{:^20s}{:^12s}{:>15s}\n"
    first_value_fmtstr = "{:^3d}{:>17.10f}{:>15.10f}{:^12s}"
    value_fmtstr = "{:^3d}{:>17.10f}{:>15.10f}{:>15.4}"

    # Print column titles
    print(label_fmtstr.format("i", "xi", "ei = |xi-r|", "ei/(e(i-1))^2"))
    current = x0
    preverror = abs(x0 - r)

    print(first_value_fmtstr.format(0, current, preverror, ""))
    for i in range(1, n+1):
        current = nextIterate(current)
        currenterror = abs(current - r)
        print(value_fmtstr.format(i, current, currenterror,
                                  currenterror / preverror ** 2))
        preverror = currenterror

    current = next
    preverror = currenterror

    return current


def fpi(g, x0, iters, decplaces):
    """
    Program 1.2 Fixed-Point Iteration
    Computes approximate solution of g(x)=x

    Computes and prints k iterations of g starting at x0

    Args:
        g: the function to be iterated
        x0: initial value of x
        iters: number of iterations
        decplaces: number of decimal digits to display
    Return:
        final value of x after iters steps
    """

    formatstr = '{:3d}{: ' + str(decplaces + 7) + \
                '.' + str(decplaces) + 'f}'

    x = x0;
    print(formatstr.format(0, x))
    for i in range(1, iters + 1):
        x = g(x)
        print(formatstr.format(i, x))
    return x


def cobweb(f, start, steps):
    """
    Computes the points on the cobweb diagram for interating
    the funcion f from start. Initial step is from the diagonal
    to the graph. Subsequent steps are two legs of the web,
    from the graph to the diagonal and back to the graph

    Args:
        f:  function to be iterated
        start: initial value of the argument
        steps:  number of steps aroung the cobweb

    Returns: a list of the consecutive points on the cobweb
    """

    Web = [(start, start)]
    Web.append((Web[-1][1], f(Web[-1][1])))

    for i in range(steps):
        Web.append((Web[-1][1], Web[-1][1]))
        Web.append((Web[-1][1], f(Web[-1][1])))

    return Web

# Some functions for testing

def logistic(c):
    def f(x):
        return c * x * (1 - x)
    return f


def squarePlus(c):
    def f(x):
        return x * x + c
    return f


def h(c):
   def f(x):
      return np.log(-x+c)
   return f

def cubic(c):
    def f(x):
        return (2*x+c)**1/3
    return f

