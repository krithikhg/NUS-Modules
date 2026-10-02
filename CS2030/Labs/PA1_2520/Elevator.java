class Elevator {
    private final int level;
    private final int time;
    private final InfList<Action> actions;

    Elevator(int level, int time, InfList<Action> actions) {
        this.level = level;
        this.time = time;
        this.actions = actions;
    }

    Elevator(Elevator elevator) {
        this.level = elevator.level;
        this.time = elevator.time;
        this.actions = elevator.actions;
    }

    public void printActions() {
        actions.forEach(x -> System.out.println(x));
    }

    public Elevator deploy() {
        Maybe<Action> firstAction = this.actions.reduce((x, y) -> x.compareTo(y) < 0 ? x : y);
        Maybe<Integer> count =
            firstAction.map(a -> this.actions.filter(x -> x.equals(a)).reduce(0, (x, y) -> x + 1));
        Maybe<InfList<Action>> actions =
            firstAction.map(a -> this.actions.filter(x -> !x.equals(a)));
        Maybe<Integer> level = firstAction.flatMap(a
            -> InfList.iterate(this.level, x -> x + 1)
                   .filter(x -> a.equals(new Action(x)))
                   .findFirst());
        Elevator out = level
                           .map(x
                               -> new Elevator(x, this.time + (x - this.level) + count.orElse(0),
                                   actions.orElse(InfList.of())))
                           .orElse(this);
        return out;
    }

    public Elevator fetch(int level) {
        if (this.level >= level) {
            return this;
        }

        return new Elevator(
            this.level, this.time, this.actions.concat(InfList.of(new FetchAction(level))));
    }

    public Elevator fetch(InfList<Integer> levels) {
        InfList<FetchAction> levelActions =
            levels.map(x -> new FetchAction(x))
                .filter(x -> x.compareTo(new FetchAction(this.level)) > 0);
        return new Elevator(this.level, this.time, this.actions.concat(levelActions));
    }

    @Override
    public String toString() {
        int level = this.level < 0 ? -this.level : this.level;
        return "Elevator: L" + level + "@" + this.time;
    }
}
