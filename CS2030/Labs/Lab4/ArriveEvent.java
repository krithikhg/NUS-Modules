class ArriveEvent extends Event {
    ArriveEvent(Customer cust, double time) {
        super(cust, time);
    }

    @Override
    Pair<Maybe<Event>, Shop> next(Shop shop) {
        return shop.findServer(this.cust)
            .map(server -> new Pair<Maybe<Event>, Shop>(
                Maybe.of(new ServeEvent(this.cust, server, this.time)),
                shop.update(server.reserve())))
            .orElseGet(() -> shop.findQueue()
                .map(server -> new Pair<Maybe<Event>, Shop>(
                    Maybe.of(new WaitEvent(this.cust, server, this.time)),
                    shop.update(server.enqueue(this.cust))))
                .orElseGet(() -> new Pair<Maybe<Event>, Shop>(
                    Maybe.of(new LeaveEvent(this.cust, this.time)), shop)));
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust + " arrives";
    }
}
