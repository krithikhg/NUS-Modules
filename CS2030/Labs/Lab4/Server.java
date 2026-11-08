import java.util.function.Supplier;

class Server {
    private final int id;
    private final int qmax;
    private final int qsize;
    private final InfList<Customer> queue;
    private final boolean occupied;
    private final Supplier<Double> serviceTime;
    private final double busyTill;

    private static final int one = 1;
    private static final int two = 2;

    Server(int id, int qmax, Supplier<Double> serviceTime) {
        this.id = id;
        this.qmax = qmax;
        this.qsize = 0;
        this.queue = InfList.iterate(1, x -> x + 1).limit(0).map(x -> new Customer(x, 0.0));
        this.occupied = false;
        this.serviceTime = serviceTime;
        this.busyTill = 0.0;
    }

    private Server(int id, int qmax, int qsize, InfList<Customer> queue, boolean occupied,
        Supplier<Double> serviceTime, double busyTill) {
        this.id = id;
        this.qmax = qmax;
        this.qsize = qsize;
        this.queue = queue;
        this.occupied = occupied;
        this.serviceTime = serviceTime;
        this.busyTill = busyTill;
    }

    boolean canServe() {
        return !this.occupied;
    }

    boolean canQueue() {
        return this.qsize < this.qmax;
    }

    Server reserve() {
        return new Server(
            this.id, this.qmax, this.qsize, this.queue, true, this.serviceTime, this.busyTill);
    }

    Server enqueue(Customer cust) {
        InfList<Customer> updatedQueue =
            InfList.iterate(0, x -> x + 1)
                .limit(this.two)
                .flatMap(x -> x == 0 ? this.queue : InfList.iterate(cust, c -> c).limit(this.one));
        return new Server(this.id, this.qmax, this.qsize + 1, updatedQueue, this.occupied,
            this.serviceTime, this.busyTill);
    }

    Maybe<Pair<Customer, Server>> takeNext() {
        return this.queue.findFirst().map(cust
            -> new Pair<>(cust,
                new Server(this.id, this.qmax, this.qsize - 1,
                    this.queue.filter(waiting -> waiting.compareTo(cust) != 0), true,
                    this.serviceTime, this.busyTill)));
    }

    Server serve(double startTime) {
        return new Server(this.id, this.qmax, this.qsize, this.queue, true, this.serviceTime,
            startTime + this.serviceTime.get());
    }

    Server finish() {
        return new Server(
            this.id, this.qmax, this.qsize, this.queue, false, this.serviceTime, this.busyTill);
    }

    boolean is(Server server) {
        return this.id == server.id;
    }

    double busyTill() {
        return this.busyTill;
    }

    @Override
    public String toString() {
        return "server " + this.id;
    }
}
