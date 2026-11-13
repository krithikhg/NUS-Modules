import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

class Dist<T> {
    private final List<T> list;
    private final List<T> distinctList;
    private final double count;
    private final double distinctCount;

    static <T> Dist<T> of(List<T> list) {
        return new Dist<T>(list);
    }

    public void forEach() {
        forEachPrint(0);
    }

    public double query(Predicate<T> pred) {
        return this.list.stream().map(x -> pred.test(x) ? 1.0 : 0.0).reduce(0.0, (x, y) -> x + y)
            / this.count;
    }

    public <R> Dist<R> map(Function<? super T, R> func) {
        return new Dist<R>(this.list.stream().map(func).toList());
    }

    public Dist<T> filter(Predicate<? super T> pred) {
        return new Dist<T>(this.list.stream().filter(pred).toList());
    }

    public <R> Dist<R> flatMap(Function<? super T, Dist<R>> func) {
        return new Dist<R>(this.list.stream().flatMap(x -> func.apply(x).list.stream()).toList());
    }

    public Dist<T> roll(int n, BinaryOperator<T> op) {
        return rollHelper(n, op, this);
    }

    private Dist<T> rollHelper(int n, BinaryOperator<T> op, Dist<T> dist) {
        if (n == 1) {
            return this;
        } else {
            return this.flatMap(x -> dist.map(y -> op.apply(x, y))).rollHelper(n - 1, op, dist);
        }
    }

    private void forEachPrint(int i) {
        if (i == this.distinctCount) {
            return;
        } else {
            System.out.println(new Event<T>(
                this.distinctList.get(i), this.query(x -> x.equals(this.distinctList.get(i)))));
            forEachPrint(i + 1);
        }
    }

    private Dist(List<T> list) {
        this.count = list.size();
        this.list = list;
        this.distinctList = list.stream().distinct().toList();
        this.distinctCount = this.distinctList.size();
    }

    @Override
    public String toString() {
        return this.distinctList.toString();
    }
}
