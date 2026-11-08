import java.util.function.Supplier;

class Simulator {
    private final InfList<Pair<Integer, Double>> arrivals;
    private final Shop shop;

    Simulator(int numServers, int qmax, int numCustomers,
        InfList<Pair<Integer, Double>> arrivals, Supplier<Double> serviceTime) {
        this.arrivals = arrivals;
        this.shop = new Shop(numServers, serviceTime, qmax);
    }

    Maybe<String> run() {
        PQ<Event> pq = this.arrivals.reduce(new PQ<Event>(),
            (queue, arrival) -> queue.add(
                new ArriveEvent(new Customer(arrival.t(), arrival.u()), arrival.u())));
        State init = new State(pq, this.shop);
        return InfList.iterate(init, state -> state.next())
            .takeWhile(state -> !state.isEmpty())
            .reduce((previous, current) -> current)
            .map(state -> state.result());
    }
}
