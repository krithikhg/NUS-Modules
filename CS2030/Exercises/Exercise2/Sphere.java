class Sphere implements Shape3D {
    private final double radius;
    private static final double VOL_RATIO = 4.0 / 3.0;
    private static final int CUBE_POW = 3;

    Sphere(double radius) {
        this.radius = radius;
    }

    public double volume() {
        return VOL_RATIO * Math.PI * Math.pow(this.radius, CUBE_POW);
    }

    @Override
    public String toString() {
        return "sphere [" + String.format("%.2f", this.radius) + "]";
    }
}
