class Sub extends Operator {
    private static final int precedence = 2;

    Sub() {
    }
    
    public int operate(int x, int y) {
        return x - y;
    }

    public int precedence() {
        return this.precedence;
    }
}
