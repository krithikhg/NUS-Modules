import java.util.Optional;

class CalcEval {
    private final Value firstVal;
    private final Operator operator;
    private final Value secondVal;

    CalcEval(Value firstVal, Operator operator, Value secondVal) {
        this.firstVal = firstVal;
        this.operator = operator;
        this.secondVal = secondVal;
    }

    public CalcEval val(int digit) {
        return new CalcEval(this.firstVal, this.operator, this.secondVal.val(digit));
    }

    public Calc2 operate(Operator operator) {
        return new Calc2(
                new Calc2(this.firstVal, Optional.of(this.operator), this.firstVal, Optional.empty()),
                this.secondVal)
            .operate(operator);
    }

    public Result equal() {
        return new Result(this.firstVal, this.operator, this.secondVal);
    }

    public String toString() {
        return this.secondVal.toString();
    }
}
