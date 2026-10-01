void main() {}

Stream<Booking> findBestBooking(Request request, List<Driver> drivers) {
    return drivers.stream().map(x -> new Booking(x, request)).sorted();
}
