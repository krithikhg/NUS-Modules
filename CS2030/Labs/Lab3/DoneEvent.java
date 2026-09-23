class DoneEvent extends Event {
    DoneEvent(Customer cust, double time) {
        super(cust, time);
    }

    @Override
    boolean isTerminal() {
        return true;
    }

    @Override
    public String toString() {
        return super.time + " " + this.cust + " done";
    }
}
