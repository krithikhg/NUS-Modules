class Circle {
    private static final double epsilon = 1E-15;
    private final Point centre;
    private final double radius;

    Circle(Point centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }

    boolean contains(Point p) {
        return p.distanceTo(this.centre) < this.radius + epsilon;
    }

    public String toString() {
        return "circle of radius " + this.radius + " centred at " + this.centre;
    }
}
