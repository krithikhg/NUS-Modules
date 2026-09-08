class Customer {
    private final int id;
    private final double arrivalTime;
    private static final double MARGIN = 1e-9;
    
    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }
    
    boolean canBeServed(double time) {
        return time <= this.arrivalTime + MARGIN;
    }

    double serveTill(double serviceTime) {
        return this.arrivalTime + serviceTime;
    }

    public String toString() {
        return "customer " + this.id;
    }
}
