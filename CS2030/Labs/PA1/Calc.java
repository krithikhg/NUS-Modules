class Calc {
    private final Value val;

    Calc() {
        this.val = new Value(0);
    }

    Calc(Value val) {
        this.val = val;
    }

    public Calc val(int digit) {
        return new Calc(this.val.val(digit));
    }

    public CalcOp operate(Operator operator) {
        return new CalcOp(this.val, operator);
    }

    public String toString() {
        return this.val.toString();
    }
}

