class Mul extends Operator {
    private static final int precedence = 1;

    Mul() {
    }

    public int operate(int x, int y) {
        return x * y;
    }

    public int precedence() {
        return this.precedence;
    }
}
