class State {
    private final PQ<Event> pq;
    private final Shop shop;
    private final String history;
    private final boolean end;

    State(Shop shop) {
        this(new PQ<Event>(), shop, "", false);
    }

    State(PQ<Event> pq, Shop shop) {
        this(pq, shop, "", false);
    }

    private State(PQ<Event> pq, Shop shop, String history, boolean end) {
        this.pq = pq;
        this.shop = shop;
        this.history = history;
        this.end = end;
    }

    boolean isEmpty() {
        return this.end;
    }

    State next() {
        Pair<Maybe<Event>, PQ<Event>> p = this.pq.poll();
        Maybe<Event> head = p.t();
        PQ<Event> nextPQ = p.u();

        return head
            .map(event -> {
                if (event.isTerminal()) {
                    return new State(nextPQ, this.shop, this.history + event + "\n", false);
                }
                Pair<Event, Shop> nxt = event.next(this.shop);
                return new State(nextPQ.add(nxt.t()), nxt.u(), this.history + event + "\n", false);
            })
            .orElseGet(() -> new State(this.pq, this.shop, this.history, true));
    }

    public String toString() {
        return this.history;
    }
}
