class ServeEvent extends Event {
    private final Server server;

    ServeEvent(Customer cust, Server server, double time) {
        super(cust, time);
        this.server = server;
    }

    @Override
    Pair<Maybe<Event>, Shop> next(Shop shop) {
        return shop.findServer(this.server).map(server -> {
            Server updated = server.serve(this.time);
            return new Pair<Maybe<Event>, Shop>(
                Maybe.of(new DoneEvent(this.cust, updated, updated.busyTill(), this)),
                shop.update(updated));
        }).orElseGet(() -> super.next(shop));
    }

    @Override
    Maybe<Event> nextService(Customer cust, Server server, double time) {
        return Maybe.of(new ServeEvent(cust, server, time));
    }

    @Override
    Statistics updateStatistics(Statistics statistics) {
        return statistics.served(this.cust.waitingTime(this.time));
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust + " serve by " + this.server;
    }
}
