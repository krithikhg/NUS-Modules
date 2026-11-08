import java.util.Scanner;
import java.util.function.Supplier;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numOfServers = sc.nextInt();
        int qmax = sc.nextInt();
        int numOfCustomers = sc.nextInt();
        Supplier<Double> serviceTime = () -> 1.0;
        sc.nextLine();
        InfList<Pair<Integer, Double>> arrivals = InfList.iterate(1, x -> x + 1)
            .limit(numOfCustomers)
            .map(x -> new Pair<>(sc.nextInt(), sc.nextDouble()));
        new Simulator(numOfServers, qmax, numOfCustomers, arrivals, serviceTime)
            .run().ifPresent(x -> System.out.println(x));
    }
}
