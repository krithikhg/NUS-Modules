class LeaveEvent extends Event {
    LeaveEvent(Customer cust, double time) {
        super(cust, time);
    }

    @Override
    boolean isTerminal() {
        return true;
    }

    public String toString() {
        return super.time + " " + cust + " leaves";
    }
}
