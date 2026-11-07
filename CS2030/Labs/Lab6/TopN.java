import java.lang.IllegalStateException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class TopN<T> {
    private final List<T> list;
    private final int top;

    TopN(int top) {
        this(List.<T>of(), top);
    }

    private TopN(List<T> list, int top) {
        this.list = list;
        this.top = top;
    }

    Try<TopN<T>> add(T str) {
        return Try.<TopN<T>>of(() -> {
            if (list.size() + 1 <= top) {
                List<T> newList = Stream.concat(list.stream(), Stream.of(str)).toList();
                return new TopN<T>(newList, top);
            } else {
                throw new IllegalStateException("out of range");
            }
        });
    }

    Try<T> get(int i) {
        return Try.<T>of(() -> {
            if (i > top || i >= list.size()) {
                throw new IllegalStateException("index " + i + " out of range");
            } else {
                return list.get(i);
            }
        });
    }

    Try<Integer> find(T s) {
        return Try.<Integer>of(() -> {
            return IntStream.range(0, list.size())
                .filter(i -> list.get(i).equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(s + " not among top " + this.top));
        });
    }

    public String toString() {
        return this.list.toString();
    }
}
