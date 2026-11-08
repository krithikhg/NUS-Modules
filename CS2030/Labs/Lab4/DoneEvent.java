class DoneEvent extends Event {
    private final Server server;
    private final Event serviceEvent;

    DoneEvent(Customer cust, Server server, double time, Event serviceEvent) {
        super(cust, time);
        this.server = server;
        this.serviceEvent = serviceEvent;
    }

    @Override
    Pair<Maybe<Event>, Shop> next(Shop shop) {
        return shop.findServer(this.server).map(server -> server.takeNext()
            .map(pair -> new Pair<Maybe<Event>, Shop>(
                this.serviceEvent.nextService(pair.t(), pair.u(), this.time),
                shop.update(pair.u())))
            .orElseGet(() -> new Pair<Maybe<Event>, Shop>(
                Maybe.<Event>empty(), shop.update(server.finish()))))
            .orElseGet(() -> super.next(shop));
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust + " done";
    }
}
