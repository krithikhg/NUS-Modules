class Booking implements Comparable<Booking> {
    private final double fare;
    private final Driver driver;
    private final Request request;
    private final Service service;

    Booking(Driver driver, Request request) {
        this.driver = driver;
        this.request = request;
        this.service = this.driver.rankBestServices(request)
                           .findFirst()
                           .map(x -> x.t())
                           .orElse(new TakeACab());
        this.fare = request.computeFare(this.service);
    }

    @Override
    public int compareTo(Booking other) {
        if (this.fare != other.fare) {
            return Double.compare(this.fare, other.fare);
        }
        return this.driver.compareTo(other.driver);
    }

    @Override
    public String toString() {
        double fareInDollars = this.fare / 100;
        return "$" + String.format("%.2f", fareInDollars) + " using " + driver + " (" + this.service
            + ")";
    }
}
