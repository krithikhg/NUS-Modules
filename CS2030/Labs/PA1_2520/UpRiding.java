class UpRiding extends Elevator {
    UpRiding(int level, int time, InfList<Integer> actions) {
        super(level, time, actions.map(x -> new Action(x)));
    }

    private UpRiding(Elevator elevator) {
        super(elevator);
    }

    @Override
    public UpRiding deploy() {
        return new UpRiding(super.deploy());
    }

    @Override
    public UpRiding fetch(int level) {
        return new UpRiding(super.fetch(level));
    }

    @Override
    public UpRiding fetch(InfList<Integer> levels) {
        return new UpRiding(super.fetch(levels));
    }
}
