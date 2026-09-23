class Shop {
    private final InfList<Server> servers;

    Shop(int numServers, double serviceTime) {
        this.servers =
            InfList.iterate(1, x -> x + 1).limit(numServers).map(x -> new Server(x, serviceTime));
    }

    Shop(InfList<Server> servers) {
        this.servers = servers;
    }

    Maybe<Server> findServer(Customer cust) {
        return this.servers.filter(x -> x.canServe(cust)).findFirst();
    }

    Shop update(Server server) {
        InfList<Server> newList = this.servers.map(x -> (x.is(server) ? server : x));
        return new Shop(newList);
    }

    public String toString() {
        return "Shop:" + this.servers.map(x -> "<" + x + ">").reduce("", (x, y) -> x + y);
    }
}
