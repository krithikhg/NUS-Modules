class Server {
    private final int id;
    private final double serviceTime;
    private final double busyTill;

    Server(int id, double serviceTime) {
        this.id = id;
        this.serviceTime = serviceTime;
        this.busyTill = 0.0;
    }

    Server(int id, double serviceTime, double busyTill) {
        this.id = id;
        this.serviceTime = serviceTime;
        this.busyTill = busyTill;
    }

    public Server serve(Customer cust) {
        return new Server(this.id, this.serviceTime, cust.serveTill(this.serviceTime));
    }

    public boolean canServe(Customer cust) {
        return cust.canBeServed(busyTill);
    }

    public boolean is(Server server) {
        return this.id == server.id;
    }

    public double busyTill() {
        return this.busyTill;
    }

    public String toString() {
        return "server " + this.id;
    }
}
