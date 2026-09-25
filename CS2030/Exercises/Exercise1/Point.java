class Point {
    private final double x;
    private final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Point midPoint(Point other) {
        return new Point((this.x + other.x) / 2, (this.y + other.y) / 2);
    }

    double angleTo(Point other) {
        return Math.atan2(other.y - this.y, other.x - this.x);
    }

    double distanceTo(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    Point moveTo(double theta, double d) {
        return new Point((this.x + d * Math.cos(theta)), (this.y + d * Math.sin(theta)));
    }

    @Override
    public String toString() {
        return "point (" + String.format("%.3f", this.x) + ", " + String.format("%.3f", this.y)
            + ")";
    }
}
