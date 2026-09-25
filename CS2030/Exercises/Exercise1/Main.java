import java.util.List;

static double epsilon = 1E-15; // declare epsilon as a constant

Circle createUnitCircle(Point p, Point q) {
    Point m = p.midPoint(q);
    double theta = p.angleTo(q);
    double mc = Math.sqrt(1.0 - Math.pow(m.distanceTo(p), 2.0));
    return new Circle(m.moveTo(theta + Math.PI / 2.0, mc), 1.0);
}

int findCoverage(Circle c, List<Point> points) {
    return points.stream().map(x -> c.contains(x) ? 1 : 0).reduce(0, (x, y) -> x + y);
}

int findMaxDiscCoverage(List<Point> points) {
    return points.stream()
        .flatMap(x -> points.stream().map(y -> findCoverage(createUnitCircle(x, y), points)))
        .reduce(-1, (x, y) -> y > x ? y : x);
}

void main() {}
