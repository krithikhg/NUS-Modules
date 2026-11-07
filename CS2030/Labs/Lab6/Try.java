import java.util.function.Function;
import java.util.function.Supplier;

interface Try<T> {
    static <T> Try<T> of(Supplier<? extends T> supplier) {
        try {
            // supplier needs to be exception free to create succ
            return succ(supplier.get());
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    static <T, R> boolean equals(Try<T> t1, Try<R> t2) {
        return t1.tryCatch(value1
            -> t2.tryCatch(value2 -> value1.equals(value2), ex2 -> false),
            ex1 -> t2.tryCatch(value2 -> false, ex2 -> ex1.getMessage().equals(ex2.getMessage())));
    }

    private static <T> Try<T> succ(T t) {
        return new Try<T>() {
            public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
                R r = mapper.apply(t);
                return succ(r);
            }

            public <R> Try<R> flatMap(Function<? super T, ? extends Try<? extends R>> mapper) {
                Try<? extends R> tr = mapper.apply(t);
                return tr.map(Function.<R>identity());
            }

            public <R> R tryCatch(
                Function<? super T, ? extends R> succ, Function<Exception, ? extends R> fail) {
                return succ.apply(t);
            }

            public String toString() {
                return "Success: " + t;
            }
        };
    }

    private static <T> Try<T> fail(Exception ex) {
        return new Try<T>() {
            public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
                return fail(ex);
            }

            public <R> Try<R> flatMap(Function<? super T, ? extends Try<? extends R>> mapper) {
                return fail(ex);
            }

            public <R> R tryCatch(
                Function<? super T, ? extends R> succ, Function<Exception, ? extends R> fail) {
                return fail.apply(ex);
            }

            public String toString() {
                return "Failure: " + ex;
            }
        };
    }

    public <R> Try<R> map(Function<? super T, ? extends R> mapper);

    public <R> Try<R> flatMap(Function<? super T, ? extends Try<? extends R>> mapper);

    public <R> R tryCatch(
        Function<? super T, ? extends R> succ, Function<Exception, ? extends R> fail);
}
