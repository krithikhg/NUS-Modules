class CalcOp {
    private final Value val;
    private final Operator operator;

    CalcOp(Value val, Operator operator) {
        this.val = val;
        this.operator = operator;
    }

    public CalcEval val(int digit) {
        return new CalcEval(this.val, this.operator, new Value(digit));
    }

    public String toString() {
        return this.val.toString();
    }



}
