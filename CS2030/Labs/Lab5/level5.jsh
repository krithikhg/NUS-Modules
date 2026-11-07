Log<Integer> sum(int n) {
    if (n == 0) {
        return Log.of(0, "hit base case!");
    }
    return sum(n - 1)
        .flatMap(total -> Log.of(total + n, "adding " + n));
}

Log<Integer> f(int n) {
    if (n == 1) {
        return Log.of(1, "1");
    } else if (n % 2 == 0) {
        return Log.of(n / 2, n + " / 2")
            .flatMap(x -> f(x));
    } else {
        return Log.of(3 * n + 1, "3(" + n + ") + 1")
            .flatMap(x -> f(x));
    }
}
