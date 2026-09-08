import java.util.Scanner;

static final double SERVICE_TIME = 1.0;

void main() {
    Scanner sc = new Scanner(System.in);
    int numOfServers = sc.nextInt();
    int numOfCustomers = sc.nextInt();

    sc.nextLine(); // removes trailing newline
    InfList<Pair<Integer,Double>> arrivals = InfList.iterate(1, x -> x + 1)
        .limit(numOfCustomers)
        .map(x -> new Pair<Integer,Double>(sc.nextInt(), sc.nextDouble()));

    State state = new Simulator(numOfServers, numOfCustomers, arrivals, SERVICE_TIME)
        .run();
    System.out.println(state);
}
