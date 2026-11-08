import java.util.function.Supplier;

class Shop {
    private final InfList<Server> servers;

    Shop(int numServers, Supplier<Double> serviceTime, int qmax) {
        this.servers = InfList.iterate(1, x -> x + 1).limit(numServers)
            .map(x -> new Server(x, qmax, serviceTime));
    }

    private Shop(InfList<Server> servers) {
        this.servers = servers;
    }

    Maybe<Server> findServer(Customer cust) {
        return this.servers.filter(server -> server.canServe()).findFirst();
    }

    Maybe<Server> findServer(Server target) {
        return this.servers.filter(server -> server.is(target)).findFirst();
    }

    Maybe<Server> findQueue() {
        return this.servers.filter(server -> server.canQueue()).findFirst();
    }

    Shop update(Server server) {
        return new Shop(this.servers.map(x -> x.is(server) ? server : x));
    }

    @Override
    public String toString() {
        return "Shop:" + this.servers.map(x -> "<" + x + ">").reduce("", (x, y) -> x + y);
    }
}
