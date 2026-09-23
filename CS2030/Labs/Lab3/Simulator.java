class Simulator {
    private final int numServers;
    private final int numCustomers;
    private final InfList<Pair<Integer, Double>> arrivals;
    private final double serviceTime;
    private final Shop shop;

    Simulator(int numServers, int numCustomers, InfList<Pair<Integer, Double>> arrivals,
        double serviceTime) {
        this.numServers = numServers;
        this.numCustomers = numCustomers;
        this.arrivals = arrivals;
        this.serviceTime = serviceTime;
        this.shop = new Shop(numServers, serviceTime);
    }

    Maybe<String> run() {
        PQ<Event> pq = arrivals.reduce(
            new PQ<Event>(), (x, y) -> x.add(new ArriveEvent(new Customer(y.t(), y.u()), y.u())));
        State init = new State(pq, this.shop);

        return InfList.iterate(init, state -> state.next())
            .takeWhile(state -> !state.isEmpty())
            .map(state -> state.toString())
            .reduce((x, y) -> y);
    }
}
