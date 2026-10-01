import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

abstract class Driver implements Comparable<Driver> {
    private final String licensePlate;
    private final int waitingTime;
    private final String driverType;
    private final List<Service> services;

    Driver(String licensePlate, int waitingTime, String driverType, List<Service> services) {
        this.licensePlate = licensePlate;
        this.waitingTime = waitingTime;
        this.driverType = driverType;
        this.services = services;
    }

    Stream<Pair<Service, Integer>> rankBestServices(Request request) {
        return services.stream()
            .map(x -> new Pair<Service, Integer>(x, request.computeFare(x)))
            .sorted(Comparator.comparing(x -> x.u()));
    }

    @Override
    public int compareTo(Driver other) {
        return this.waitingTime - other.waitingTime;
    }

    @Override
    public String toString() {
        return this.licensePlate + " (" + this.waitingTime + " mins away) " + this.driverType;
    }
}
