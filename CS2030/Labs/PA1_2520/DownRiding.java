class DownRiding extends Elevator {
    DownRiding(int level, int time, InfList<Integer> actions) {
        super(-level, time, actions.map(x -> new Action(-x)));
    }

    private DownRiding(Elevator elevator) {
        super(elevator);
    }

    @Override
    public DownRiding deploy() {
        return new DownRiding(super.deploy());
    }

    @Override
    public DownRiding fetch(int level) {
        return new DownRiding(super.fetch(-level));
    }

    @Override
    public DownRiding fetch(InfList<Integer> levels) {
        return new DownRiding(super.fetch(levels.map(x -> - x)));
    }
}
