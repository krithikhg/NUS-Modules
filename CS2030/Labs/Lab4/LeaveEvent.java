class LeaveEvent extends Event {
    LeaveEvent(Customer cust, double time) {
        super(cust, time);
    }

    @Override
    Statistics updateStatistics(Statistics statistics) {
        return statistics.left();
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust + " leaves";
    }
}
