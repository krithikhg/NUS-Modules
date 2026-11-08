class Statistics {
    private final double totalWaitingTime;
    private final int served;
    private final int left;

    Statistics() {
        this(0.0, 0, 0);
    }

    private Statistics(double totalWaitingTime, int served, int left) {
        this.totalWaitingTime = totalWaitingTime;
        this.served = served;
        this.left = left;
    }

    Statistics served(double waitingTime) {
        return new Statistics(this.totalWaitingTime + waitingTime, this.served + 1, this.left);
    }

    Statistics left() {
        return new Statistics(this.totalWaitingTime, this.served, this.left + 1);
    }

    @Override
    public String toString() {
        double average = this.served == 0 ? 0.0 : this.totalWaitingTime / this.served;
        return String.format("[%.3f %d %d]", average, this.served, this.left);
    }
}
