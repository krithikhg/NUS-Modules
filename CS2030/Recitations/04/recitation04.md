# Recitation 4

## Question 1

a) lambda expression:

```java 
IntPredicate and(IntPredicate p1, IntPredicate p2) {
    return x -> p1.test(x) && p2.test(x);
}
```
b) Anonymous inner class:

```java
IntPredicate and(IntPredicate p1, IntPredicate p2) {
    return new IntPredicate() {
        public boolean test(int x) {
            return p1.test(x) && p2.test(x);
        }
    };
}
```

c) Concrete class implementation:

```java
IntPredicate and(IntPredicate p1, IntPredicate p2) {
    class andPredicate implements IntPredicate {
        IntPredicate p1;
        IntPredicate p2;

        andPredicate(IntPredicate p1, IntPredicate p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
        public boolean test(int x) {
            return this.p1.test(x) && this.p2.test(x);
        }
    };
    return new andPredicate(p1, p2);
}
```

## Question 2

a) You can't add an Object to any list that contains types that extend object. You can only add a new Object to a List<Object>

b) You can't add a String to any list that extends object, only List<String>. and you cant get a string also

c) again you can only add a new object to a list<object>

d) No because you can't add a String to any List, only a List<String>

e) You can't have an ArrayList<int> in the first place since int is a primitive. You also can't assign ArrayList<Integer> to any List<? super Integer> only List<Integer>. Generic type parameters must be objects (reference types) not primitives.

f) This compiles, but gives a warning. Should be avoided. 