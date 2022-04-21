import math
import asyncio
import datetime

name = "Edgar"

print("Hello, %s" %name)
print("Hello {}".format(name))
print(f"Hello {name}")


animals = ['cow', 'horse', 'dog', 'cat']

for count, animal in enumerate(animals):
    print(count, animal)


class Circle:
    def __init__(self, radius):
        self.radius = radius
    def area(self):
        return math.pi * self.radius * self.radius

    def circunference(self):
        return math.pi * pow(self.radius, 2)

    def __str__(self):
        return f"radious: {self.radius} area: {self.area()} circunference: {self.circunference()}"

    def __repr__(self):
        return f" Object: {type(self)} Attributes {self.__dict__}:"


c = Circle(10)

print(c.circunference())
print(str(c))
print(repr(c))


class myObj:
  name = "John"

y = myObj()

x = isinstance(y, myObj)
print(x)


class Polygon():
    
    def __init__(self, polygonType):
        self.type = polygonType;
        print('Polygon is a ', polygonType)




class Triangle(Polygon):
  def __init__(self):

    Polygon.__init__('triangle')
    
print(issubclass(Triangle, Polygon))

poligon = Polygon("square")
print(poligon.__dict__)
poligon.type2 = "circle"
print(poligon.__dict__)

print([cls.__name__ for cls in Polygon.__subclasses__()])




def add(a=0, b=0):
   return a + b

d= {'a': 2, 'b': 3}
print(add(**d))



class PowTwo:


    def __init__(self, max=0):
        self.max = max

    def __iter__(self):
        self.n = 0
        return self

    def __next__(self):
        if self.n <= self.max:
            result = 2 ** self.n
            self.n += 1
            return result
        else:
            raise StopIteration


numbers = PowTwo(3)

i = iter(numbers)

print(next(i))
print(next(i))
print(next(i))
print(next(i))




async def main():
    print('hello', datetime.datetime.now())
    await asyncio.sleep(2)
    print('world', datetime.datetime.now())

asyncio.run(main())


def memoize_factorial(f): 
    memory = {} 
   
    def inner(num): 
        if num not in memory:          
            memory[num] = f(num) 
        return memory[num] 
  
    return inner 
      
@memoize_factorial
def facto(num): 
    if num == 1: 
        return 1
    else: 
        return num * facto(num-1) 
  
print(facto(5))






