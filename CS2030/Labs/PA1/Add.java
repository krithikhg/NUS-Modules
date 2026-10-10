class Add extends Operator {
    private static final int precedence = 2;
    Add() {
    }

    public int operate(int x, int y) {
        return x + y;
    }

    public int precedence() {
        return this.precedence;
    }
}
