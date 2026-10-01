class TakeACab implements Service {
    private static final int rate = 33;
    private static final int bookingFee = 200;

    public int computeFare(int distance, int passengers, int time) {
        return distance * this.rate + this.bookingFee;
    }

    @Override
    public String toString() {
        return "TakeACab";
    }
}
