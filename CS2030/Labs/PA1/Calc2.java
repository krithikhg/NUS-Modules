import java.util.Optional;

class Calc2 {
    private final Value firstVal;
    private final Optional<Operator> firstOp;
    private final Value secondVal;
    private final Optional<Operator> secondOp;
    private final Optional<Value> thirdVal;

    Calc2() {
        this.firstVal = new Value(0);
        this.firstOp = Optional.empty();
        this.secondVal = new Value(0);
        this.secondOp = Optional.empty();
        this.thirdVal = Optional.empty();
    }

    Calc2(
        Value firstVal, Optional<Operator> firstOp, Value secondVal, Optional<Operator> secondOp) {
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
        return new Calc2(this, thirdVal.map(v -> v.val(digit)).orElse(new Value(digit)));
    }

    public Calc2 equal() {
        Operator none = new Add();
        Operator first = this.firstOp.orElse(none);
        Operator second = this.secondOp.orElse(none);
        Value out;

        if (second != none) {
            Value term =
                this.thirdVal.map(v -> this.secondVal.operate(second, v)).orElse(this.secondVal);
            out = this.firstVal.operate(first, term);
        } else if (first != none) {
            out = this.firstVal.operate(first, this.thirdVal.orElse(new Value(0)));
        } else {
            out = this.thirdVal.orElse(this.firstVal);
        }

        return new Calc2(out, Optional.empty(), out, Optional.empty());
    }

    public Calc2 operate(Operator operator) {
        Operator none = new Add();
        Value noneVal = new Value(0);
        Operator first = this.firstOp.orElse(none);
        Operator second = this.secondOp.orElse(none);
        Value third = this.thirdVal.orElse(noneVal);
        if (first == none) {
            Value value = this.thirdVal.orElse(this.firstVal);
            return new Calc2(value, Optional.of(operator), value, Optional.empty());
        }
        if (second == none) {
            if (third == noneVal) {
                return new Calc2(
                    this.firstVal, Optional.of(operator), this.firstVal, Optional.empty());
            }
            if (operator.compareTo(first) < 0) {
                return new Calc2(this.firstVal, Optional.of(first), third, Optional.of(operator));
            }
            Value out = this.firstVal.operate(first, third);
            return new Calc2(out, Optional.of(operator), out, Optional.empty());
        }
        if (third == noneVal) {
            if (operator.compareTo(first) < 0) {
                return new Calc2(
                    this.firstVal, Optional.of(first), this.secondVal, Optional.of(operator));
            }
            Value out = this.firstVal.operate(first, this.secondVal);
            return new Calc2(out, Optional.of(operator), out, Optional.empty());
        }
        if (operator.compareTo(first) < 0) {
            return new Calc2(this.firstVal, Optional.of(first),
                this.secondVal.operate(second, third), Optional.of(operator));
        }
        Value out = this.firstVal.operate(first, this.secondVal.operate(second, third));
        return new Calc2(out, Optional.of(operator), out, Optional.empty());
    }

    public String toString() {
        return this.thirdVal.map(v -> v.toString()).orElse(this.secondVal.toString());
    }
}
