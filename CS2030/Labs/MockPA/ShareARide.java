class ShareARide implements Service {
    private static final int rate = 50;
    private static final int surcharge = 500;
    private static final int peakStart = 600;
    private static final int peakEnd = 900;

    public int computeFare(int distance, int passengers, int time) {
        return Double
            .valueOf(Math.floor(
                (distance * this.rate
                    + ((time >= this.peakStart && time <= this.peakEnd) ? this.surcharge : 0))
                / passengers))
            .intValue();
    }

    @Override
    public String toString() {
        return "ShareARide";
    }
}
