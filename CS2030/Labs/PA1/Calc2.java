class Calc2 {
    private final Value firstVal;
    private final Operator firstOp;
    private final Value secondVal;
    private final Operator secondOp;
    private final Optional<Value> thirdVal;

    Calc2() {
        this.firstVal = new Value(0);
        this.firstOp = Maybe.empty();
        this.secondVal = Maybe.empty();
        this.secondOp = Maybe.empty();
        this.thirdVal = Maybe.empry
    }

    Calc2(Value firstVal, Operator firstOp, Value secondVal, Operator secondOp) {
        this.firstVal = firstVal;
        this.firstOp = firstOp;
        this.secondVal = secondVal;
        this.secondOp = secondOp;
        this.thirdVal = Optional.empty();
    }

    Calc2(Calc2 other, Value thirdVal) {
        this.firstVal = other.firstVal;
        this.firstOp = other.firstOp;
        this.secondVal = other.secondVal;
        this.secondOp = other.secondOp;
        this.thirdVal = Optional.of(thirdVal);
    }


    public Calc2 val(int digit) {
        return new Calc2(this, 
                thirdVal.map(v -> v.val(digit)).orElse(new Value(digit)));
    }

    public Result equal() {
        if (this.firstOp.compareTo(this.secondOp) < 0) {
            return new Result(this.firstVal.operate(this.firstOp, this.secondVal), this.secondOp, this.thirdVal.orElse(new Value(0)));
        }
        else {
            return new Result(this.firstVal, this.firstOp, this.secondVal.operate(this.secondOp, this.thirdVal.orElse(new Value(0))));
        }
    }

    public Calc2 operate(Operator operator) {
        if (operator.compareTo(new Mul()) > 0) {
            return new Calc2(this.firstVal);
            


        }

        else {

            return new Calc2();
        }
            

    }

    public String toString() {
        return thirdVal.map(v -> v.toString()).orElse(secondVal.toString());
    }
}
