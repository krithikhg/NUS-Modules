import java.util.Optional;
import java.util.function.Function;

class Log<T> {
    private final T value;
    private final String log;

    private Log(T value, String log) {
        this.value = value;
        this.log = log;
    }

    public static <T> Log<T> of(T value) {
        return Log.of(value, "");
    }

    public static <T> Log<T> of(T value, String log) {
        return Optional.ofNullable(value)
                .filter(x -> !(x instanceof Log<?>))
                .flatMap(x -> Optional.ofNullable(log).map(text -> new Log<T>(x, text)))
                .orElseThrow(() -> new IllegalArgumentException("Invalid arguments"));
    }

    public void log() {
        if (!this.log.isEmpty()) {
            System.out.println(this.log);
        }
    }

    public <U> Log<U> map(Function<? super T, ? extends U> mapper) {
        return Log.of(mapper.apply(this.value), this.log);
    }

    public <U> Log<U> flatMap(Function<? super T, ? extends Log<? extends U>> mapper) {
        Log<? extends U> result = mapper.apply(this.value);

        String combinedLog;
        if (this.log.isEmpty()) {
            combinedLog = result.log;
        } else if (result.log.isEmpty()) {
            combinedLog = this.log;
        } else {
            combinedLog = this.log + "\n" + result.log;
        }

        return Log.of(result.value, combinedLog);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Log<?> other) {
            return this.value.equals(other.value) && this.log.equals(other.log);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Log[" + this.value + "]";
    }
}
