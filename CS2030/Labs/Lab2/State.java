class State {
    private final Shop shop;
    private final String history;

    State(Shop shop) {
        this.shop = shop;
        this.history = "";
    }

    State(Shop shop, String history) {
        this.shop = shop;
        this.history = history;
    }

    State next(Customer cust) {
        Maybe<Server> activeServer = this.shop.findServer(cust);

        return new State(this.shop.update(activeServer.map(x -> x.serve(cust))
                    .orElse(new Server(-1, -1.0))),
                history 
                + "\n" + cust + " arrives" 
                + "\n" + activeServer.map(x -> cust + " served by " + x)
                .orElse(cust + " leaves"));
    }

    public String toString() {
        return this.history;
    }
}
