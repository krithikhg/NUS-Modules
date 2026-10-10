abstract class Operator implements Comparable<Operator> { 
    abstract int operate(int x, int y);
    abstract int precedence();

    public int compareTo(Operator other) {
        return this.precedence() - other.precedence();
    }
}
