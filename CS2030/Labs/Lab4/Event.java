class Event implements Comparable<Event> {
    private static final double MARGIN = 1e-9;
    protected final Customer cust;
    protected final double time;

    Event(Customer cust, double time) {
        this.cust = cust;
        this.time = time;
    }

    Pair<Maybe<Event>, Shop> next(Shop shop) {
        return new Pair<>(Maybe.<Event>empty(), shop);
    }

    Maybe<Event> nextService(Customer cust, Server server, double time) {
        return Maybe.<Event>empty();
    }

    Statistics updateStatistics(Statistics statistics) {
        return statistics;
    }

    @Override
    public int compareTo(Event other) {
        if (this.time < other.time - MARGIN) {
            return -1;
        }
        if (this.time > other.time + MARGIN) {
            return 1;
        }
        return this.cust.compareTo(other.cust);
    }
}
