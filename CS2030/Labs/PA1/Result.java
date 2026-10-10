class Result {
    private final Value firstVal;
    private final Operator operator;
    private final Value secondVal;
    private final Value out;

    Result(Value firstVal, Operator operator, Value secondVal) {
        this.firstVal = firstVal;
        this.operator = operator;
        this.secondVal = secondVal;
        this.out = firstVal.operate(operator, secondVal);
    }

    public String toString() {
        return this.out.toString();
    }
}
