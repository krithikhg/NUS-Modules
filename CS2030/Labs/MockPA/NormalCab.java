import java.util.List;

class NormalCab extends Driver {
    NormalCab(String licensePlate, int waitingTime) {
        super(licensePlate, waitingTime, "NormalCab", List.of(new JustRide(), new TakeACab()));
    }
}
