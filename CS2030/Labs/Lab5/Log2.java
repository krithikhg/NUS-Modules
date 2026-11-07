class Log<T> {
    private final T value;
    private final Optional<String> string;

    private Log(T value, String string) {
        this.value = value;
        this.string = Optional.of(string);
    }

    private Log(T value) {
        this.value = value;
        this.string = Optional.empty();
    }

    static <T> Log<T> of(T value) {
        return new Log<T>(value);
    }

    static <T> Log<T> of(T value, String string) {
        return Optional.ofNullable(new Log<T>(value, string))
            .orElseThrow(() -> new IllegalArgumentException("Invalid arguments"));
    }

    public void log() {
        System.out.print(this.string.map(x -> x + "\n").orElse(""));
    }

    public String toString() {
        return "Log[" + this.value + "]";
    }
}
