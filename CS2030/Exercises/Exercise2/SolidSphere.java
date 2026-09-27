class SolidSphere extends Sphere {
    private final double density;
    private final SolidImpl solidImpl;

    SolidSphere(double radius, double density) {
        super(radius);
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
