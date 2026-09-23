# CS2030 Recitation 1
## Declarative Programming with Java Streams

### Question 1

```java
boolean isPrime(int n) {
    return IntStream.range(2,n)
        .boxed()
        .allMatch(x -> n%x != 0);
}

int countPrimeFactors(int n) {
    return IntStream.rangeClosed(2,n)
        .filter(x -> isPrime(x))
        .filter(x -> n%x == 0)
        .reduce(0, (x,y) -> x+1);
}

IntStream omega(int n) {
    return IntStream.rangeClosed(1,n)
        .map(x -> countPrimeFactors(x));
}
```

### Question 2

```java
IntStream dot(int i, int j) {
    return IntStream.rangeClosed(i,j)
        .flatMap(x -> IntStream.rangeClosed(i,j).map(y -> x*y));
}
```

```java
record IntPair(int fst, int snd){}

Stream<IntPair> product(int i, int j) {
    return IntStream.rangeClosed(i,j).boxed()
        .flatMap(x -> IntStream.rangeClosed(i,j).boxed().map(y -> new IntPair(x, y)));
}
```

### Question 3

```java

Stream<Integer> fibo(int n) {
    return Stream.iterate(
        new IntPair(0,1), 
        x -> new IntPair(x.snd(), x.fst() + x.snd()))
        .limit(n)
        .map(x -> x.snd());
}