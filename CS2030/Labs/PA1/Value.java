class Value {
    private final int val;
    private static final int BASE = 10;

    Value(int val) {
        this.val = val;
    }

    public Value val(int digit) {
        int newVal = this.val < 0 ? this.val * BASE - digit : this.val * BASE + digit;
        return new Value(newVal);
    }

    public Value operate(Operator operator, Value other) {
        return new Value(operator.operate(this.val, other.val));
    }

    public String toString() {
        return "[" + this.val + "]";
    }
}
