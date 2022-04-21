"""
Program 6.1 Euler's Method for Solving Initial Value Problems
Python translation JR 1/17/12. Update for 2e 2/24/12.
Adapted for MAT 3570 Spring 2016 and Apring 2017 by Peter Andrews
ydot evaluates rhs of differential equation
Also implementations of Trapezoid, Midpoint, and RK4  algorithms
"""
from matplotlib.pyplot import plot, show, legend
import scipy as sp

def eulerstep(yprime,t,y,h):
    """
    One step in ivp plotting using Euler's Method
    Args:
        yprime: Function of t,y -- right hand side of ODE
        param t: t-coord of initial point of step
        param y: y-coord of initial point of step
        param h: t-distance of step

    Return: y-value at end of step
    """
    return y + h*yprime(t,y)

def trapezoidstep(yprime,t,y,h):
    """
    One step in ivp plotting using Trapezoid Method
    Args:
        yprime: Function of t,y -- right hand side of ODE
        param t: t-coord of initial point of step
        param y: y-coord of initial point of step
        param h: t-distance of step

    Return: y-value at end of step
    """
    s1 = yprime(t,y)
    u = y + h*s1
    s2 = yprime(t+h,u)
    s = 0.5*(s1+s2)
    return y + h*s
    

def midpointstep(yprime,t,y,h):
    """
    One step in ivp plotting using Midpoint Method
    Args:
        yprime: Function of t,y -- right hand side of ODE
        param t: t-coord of initial point of step
        param y: y-coord of initial point of step
        param h: t-distance of step

    Return: y-value at end of step
    """
    s1 = yprime(t,y)
    s2 = yprime(t+h/2, y + h*s1/2)
    return y + h*s2

def rk4step(yprime,t,y,h):
    """
    One step in ivp plotting using RK4 Method
    Args:
        yprime: Function of t,y -- right hand side of ODE
        param t: t-coord of initial point of step
        param y: y-coord of initial point of step
        param h: t-distance of step

    Return: y-value at end of step
    """
    s1 = yprime(t,y)
    s2 = yprime(t+h/2, y + h*s1/2)
    s3 = yprime(t+h/2, y + h*s2/2)
    s4 = yprime(t+h, y + h*s3)
    s = (1/6)*(s1 + 2*s2 + 2*s3 + s4)
    return y + h*s

