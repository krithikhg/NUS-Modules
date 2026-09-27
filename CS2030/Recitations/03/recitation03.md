# Recitation 3

## Question 1

a)
(i) is not allowed. s is a shape at compile time, so it does not have a scale method as it is not necessarily a Scalable. 
(ii) is allowed since k is a Scalable and can scale
(iii) is allowed
(iv) is not allowed

b)
(i) Yes since c is a Circle that implements both interfaces
(ii) No, cannot do k.getArea

c)
Would work but you would have to do that for every combination of interfaces. 

## Question 2

It is bad program design as every time we want to add a new type of shape to the Shape class we would need to change the underlying implementation of the various methods in Shape, making it very inflexible. 

## Question 3

a - true (overriden method is still called here)
b- true
c - true
d - true
e - true
f - true

## Question 4

Yes it compiles as B is an A. So the method defined in B returns a B which is still an A.

If we swap the definitions of method, however, it will not compile. Since A is not a B. 
