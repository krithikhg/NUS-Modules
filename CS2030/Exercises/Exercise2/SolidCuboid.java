class SolidCuboid extends Cuboid implements Solid {
    private final double density;
    private final SolidImpl solidImpl;

    SolidCuboid(double height, double width, double length, double density) {
        super(height, width, length);
        this.density = density;
        this.solidImpl = new SolidImpl(this, density);
    }

    public double mass() {
        return this.solidImpl.mass();
    }

    @Override
    public String toString() {
        return "solid-" + super.toString() + " with a mass of "
            + String.format("%.2f", this.mass());
    }
}
