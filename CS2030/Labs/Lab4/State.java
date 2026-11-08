class State {
    private final PQ<Event> pq;
    private final Shop shop;
    private final String history;
    private final boolean end;
    private final Statistics statistics;

    State(Shop shop) {
        this(new PQ<Event>(), shop);
    }

    State(PQ<Event> pq, Shop shop) {
        this(pq, shop, "", false, new Statistics());
    }

    private State(PQ<Event> pq, Shop shop, String history, boolean end,
        Statistics statistics) {
        this.pq = pq;
        this.shop = shop;
        this.history = history;
        this.end = end;
        this.statistics = statistics;
    }

    boolean isEmpty() {
        return this.end;
    }

    State next() {
        Pair<Maybe<Event>, PQ<Event>> polled = this.pq.poll();
        PQ<Event> remaining = polled.u();
        return polled.t().map(event -> {
            Pair<Maybe<Event>, Shop> transition = event.next(this.shop);
            PQ<Event> updated = transition.t().map(next -> remaining.add(next))
                .orElse(remaining);
            return new State(updated, transition.u(), this.history + event + "\n", false,
                event.updateStatistics(this.statistics));
        }).orElseGet(() -> new State(this.pq, this.shop, this.history, true, this.statistics));
    }

    String result() {
        return this.history + this.statistics;
    }

    @Override
    public String toString() {
        return this.history;
    }
}
