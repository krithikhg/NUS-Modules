import java.util.List;

class PrivateCar extends Driver {
    PrivateCar(String licensePlate, int waitingTime) {
        super(licensePlate, waitingTime, "PrivateCar", List.of(new JustRide(), new ShareARide()));
    }
}
