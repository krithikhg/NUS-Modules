class Event implements Comparable<Event> {
    protected final Customer cust;
    protected final double time;

    Event(Customer cust, double time) {
        this.cust = cust;
        this.time = time;
    }

    Pair<Event, Shop> next(Shop shop) {
        return new Pair<Event, Shop>(this, shop);
    }

    boolean isTerminal() {
        return false;
    }

    @Override
    public int compareTo(Event other) {
        if (this.time < other.time) {
            return -1;
        }
        if (this.time > other.time) {
            return 1;
        }
        return this.cust.compareTo(other.cust);
    }
}
