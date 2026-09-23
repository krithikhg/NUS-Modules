import java.util.List;
import java.util.Scanner;

static final double SERVICE_TIME = 1.0;

void main() {
    Scanner sc = new Scanner(System.in);
    int numOfServers = sc.nextInt();
    int numOfCustomers = sc.nextInt();

    sc.nextLine(); // removes trailing newline
    InfList<Pair<Integer,Double>> arrivals = InfList.iterate(1, x -> x + 1)
        .limit(numOfCustomers)
        .map(x -> new Pair<>(sc.nextInt(), sc.nextDouble()));

    new Simulator(numOfServers, numOfCustomers, arrivals, SERVICE_TIME)
        .run().ifPresent(x -> System.out.println(x));
}
