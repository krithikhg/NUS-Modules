class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final InfList<Pair<Integer,Double>> arrivals;
    private final double serviceTime;

    Simulator(int numOfServers, int numOfCustomers, 
            InfList<Pair<Integer,Double>> arrivals, double serviceTime) {
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
        this.serviceTime = serviceTime;
    }

    State run() {
        State init = new State(new Shop(numOfServers, this.serviceTime));
        return arrivals.map(x -> new Customer(x.t(), x.u()))
            .reduce(init, (s, c) -> s.next(c));
    }
}
