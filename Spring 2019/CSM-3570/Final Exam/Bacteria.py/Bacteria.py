"""
CSM 2170 -- Spring 2018
Assignment: Final Exam
Submitted by: Edgar Mesa Peerez
 """
import RKmethods as rk
from matplotlib.pyplot import plot, show, legend
import scipy as sp

def K4plot(interval,y0,n, yprime):
    """
    Plots approximate solution to ivp
    Args:
        interval: interval in the form [a, b] where solution is plotted
        y0: initial value
        n: number of steps
        yprime: Function of t,y -- right hand side of ODE
    Return: T, Y where T is a list of t-values and Y is the corresponding y-values
            in the approximate solution
    """
    h = (interval[1]-interval[0])/n
    T = sp.linspace(interval[0], interval[1], n+1, endpoint=True)
    Y = [y0]
    for i in range(n):
	    Y.append(rk.rk4step(yprime,T[i],Y[i],h) ) #use of the RK4 method
    return T,Y


def ydot(t,y):
    # right-hand side of differential equation
    return 0.03*y*(1-0.0004*y)+t*0
    # at any valye of t



left, right = 0, 200
steps = 30


limitx = sp.linspace(left,right,steps+1) #X values to plot the lmit line
limity = [1500 for i in range(0,limitx.size)] #For each X, add a 1500 to the Y value
y0 = [200,250,300,350,400,450,500] #initial values

#for each initial value, graph a function
for i in y0:

    T,Y = K4plot( [left, right], i, steps,ydot)
    plot(T,Y, label= 'y0 = ' +str(i))



plot(limitx,limity, label='Limit')
legend(loc="lower right")
show()