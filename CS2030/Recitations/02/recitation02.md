# Recitation 2

## Question 1

a) Line 13: p.x cannot be modified after it has been assigned a value during construction

b) Line 20: Class Q's baz method cannot access p.x since x is a private variable in P. Q should instead call foo().

## Question 2

a) No, because we don't expose the underlying implementation of the x and y coordinates. It is currently implemented as a point, but the caller does not need to know about the underlying implementation, it just calls the 2 methods. 

b) No again because the underlying implementation is not exposed.

## Question 3

(b): same prototype for both functions, cannot define identical function twice with same parameter types. Same for (d). (c) also, one is private while the other is not but it still causes a compilation error. 

## Question 4

a) 
```java
class PlainText extends FormattedText{

    PlainText(String text) {
        super(text);
    }

    @Override
    PlainText toggleUnderline() {
        return new PlainText(this.s);
    }
}
```
