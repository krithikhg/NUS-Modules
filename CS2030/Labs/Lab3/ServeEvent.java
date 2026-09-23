class ServeEvent extends Event {
    private final Server server;

    ServeEvent(Customer cust, Server server, double time) {
        super(cust, time);
        this.server = server;
    }

    @Override
    Pair<Event, Shop> next(Shop shop) {
        return new Pair<Event, Shop>(new DoneEvent(this.cust, this.server.busyTill()), shop);
    }

    public String toString() {
        return super.time + " " + this.cust + " serve by " + this.server;
    }
}
