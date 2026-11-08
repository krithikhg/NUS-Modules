class WaitEvent extends Event {
    private final Server server;

    WaitEvent(Customer cust, Server server, double time) {
        super(cust, time);
        this.server = server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust
            + " waits at " + this.server;
    }
}
