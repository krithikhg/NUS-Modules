import java.util.Optional;
import java.util.stream.Stream;

class Fraction extends AbstractNum<Frac> {
    static Fraction of(int i, int j) {
        if (AbstractNum.valid.test(i) && AbstractNum.valid.test(j)
            && Num.of(j).sub(Num.one()).isValid()) {
            return new Fraction(i, j);
        } else {
            return new Fraction(Optional.empty());
        }
    }

    public Fraction add(Fraction other) {
        return new Fraction(super.opt.flatMap(i
            -> other.opt.map(
                j -> Frac.of(i.t().mul(j.u()).add(i.u().mul(j.t())), i.u().mul(j.u())))));
    }

    public Fraction sub(Fraction other) {
        return new Fraction(
            super.opt
                .flatMap(i
                    -> other.opt.map(
                        j -> Frac.of(i.t().mul(j.u()).sub(i.u().mul(j.t())), i.u().mul(j.u()))))
                .filter(x -> x.u().isValid() && x.t().isValid() && x.u().sub(Num.one()).isValid()));
    }

    public Fraction mul(Fraction other) {
        return new Fraction(
            super.opt.flatMap(i -> other.opt.map(j -> Frac.of(i.t().mul(j.t()), i.u().mul(j.u()))))
                .filter(x -> x.u().isValid() && x.t().isValid() && x.u().sub(Num.one()).isValid()));
    }

    private Fraction(int i, int j) {
        super(Frac.of(Num.of(i), Num.of(j)));
    }

    private Fraction(Optional<Frac> opt) {
        super(opt);
    }
}
