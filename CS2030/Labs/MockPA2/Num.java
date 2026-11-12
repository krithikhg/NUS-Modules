import java.util.Optional;
import java.util.stream.Stream;

class Num extends AbstractNum<Integer> {
    static Num of(int i) {
        if (AbstractNum.valid.test(i)) {
            return new Num(i);
        } else {
            return new Num(Optional.empty());
        }
    }

    static Num zero() {
        return new Num(AbstractNum.zero());
    }

    static Num one() {
        return Num.zero().succ();
    }

    public Num succ() {
        return new Num(super.opt.map(i -> AbstractNum.s.apply(i)));
    }

    public Num sub(Num other) {
        return new Num(
            super.opt
                .flatMap(i
                    -> other.opt.flatMap(j
                        -> Optional.of(
                            Stream.iterate(AbstractNum.n.apply(j), x -> AbstractNum.s.apply(x))
                                .limit(i)
                                .reduce(AbstractNum.n.apply(j), (x, y) -> AbstractNum.s.apply(x)))))
                .filter(i -> AbstractNum.valid.test(i)));
    }

    public Num add(Num other) {
        return new Num(super.opt.flatMap(i
            -> other.opt.flatMap(j
                -> Stream.iterate(Num.zero(), m -> Num.zero())
                       .limit(j)
                       .reduce(Num.of(i), (x, y) -> x.succ())
                       .opt)));
    }

    public Num mul(Num other) {
        return new Num(super.opt.flatMap(i
            -> other.opt.flatMap(j
                -> Stream.iterate(Num.zero(), m -> Num.zero())
                       .limit(j)
                       .reduce(Num.zero(), (x, y) -> x.add(Num.of(i)))
                       .opt)));
    }

    private Num(int i) {
        super(i);
    }

    private Num(Optional<Integer> opt) {
        super(opt);
    }

    private Num(AbstractNum<Integer> num) {
        super(num.opt);
    }
}
