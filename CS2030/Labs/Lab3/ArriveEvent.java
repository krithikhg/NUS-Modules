class ArriveEvent extends Event {
    ArriveEvent(Customer cust, double time) {
        super(cust, time);
    }

    @Override
    Pair<Event, Shop> next(Shop shop) {
        return shop.findServer(this.cust)
            .map(x
                -> new Pair<Event, Shop>(new ServeEvent(this.cust, x.serve(this.cust), super.time),
                    shop.update(x.serve(cust))))
            .orElse(new Pair<Event, Shop>(new LeaveEvent(this.cust, time), shop));
    }

    public String toString() {
        return super.time + " " + cust + " arrives";
    }
}
