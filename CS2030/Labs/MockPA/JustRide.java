class JustRide implements Service {
    private static final int rate = 22;
    private static final int surcharge = 500;
    private static final int peakStart = 600;
    private static final int peakEnd = 900;

    public int computeFare(int distance, int passengers, int time) {
        return distance * this.rate
            + ((time >= this.peakStart && time <= this.peakEnd) ? this.surcharge : 0);
    }

    @Override
    public String toString() {
        return "JustRide";
    }
}
