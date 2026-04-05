import java.time.LocalDateTime;
import java.util.*;

public class RentalService {
    private static final double BASE_FARE = 3.0;

    private List<ActiveRental> activeRentals;
    private BikeService bikeService;
    private Deque<ERyderLog> systemLogStack;

    public RentalService(BikeService bikeService, Deque<ERyderLog> systemLogStack) {
        this.activeRentals = new ArrayList<>();
        this.bikeService = bikeService;
        this.systemLogStack = systemLogStack;
    }

    public boolean startRental(RegisteredUser user, String bikeId, String location) {
        if (!bikeService.reserveBike(bikeId, user.getEmail(), location)) {
            return false;
        }

        String rentalId = UUID.randomUUID().toString();
        ActiveRental rental = new ActiveRental(rentalId, user, bikeId, LocalDateTime.now());
        activeRentals.add(rental);
        System.out.println("Rental started: " + rentalId);

        // 日志：行程开始
        String logId = "TS" + System.currentTimeMillis();
        ERyderLog log = new ERyderLog(logId, "Trip started for user " + user.getEmail() + " with bike " + bikeId, LocalDateTime.now());
        systemLogStack.push(log);
        return true;
    }

    public boolean endRental(String rentalId) {
        ActiveRental rental = findActiveRental(rentalId);
        if (rental == null) {
            System.out.println("Rental not found.");
            return false;
        }
        rental.setEndTime(LocalDateTime.now());

        // 计算车费（多态调用）
        double fare = rental.getUser().calculateFare(BASE_FARE);
        System.out.printf("Trip completed. Total fare: $%.2f%n", fare);

        activeRentals.remove(rental);
        bikeService.releaseBike(rental.getBikeId());
        System.out.println("Rental ended: " + rentalId);

        // 日志：行程结束
        String logId = "TE" + System.currentTimeMillis();
        ERyderLog log = new ERyderLog(logId, "Trip ended for user " + rental.getUser().getEmail() +
                " with bike " + rental.getBikeId() + ", fare = $" + fare, LocalDateTime.now());
        systemLogStack.push(log);
        return true;
    }

    public boolean cancelRental(String rentalId) {
        ActiveRental rental = findActiveRental(rentalId);
        if (rental == null) {
            System.out.println("Rental not found.");
            return false;
        }
        activeRentals.remove(rental);
        bikeService.releaseBike(rental.getBikeId());
        System.out.println("Rental cancelled: " + rentalId);
        return true;
    }

    public List<ActiveRental> trackActiveRentals() {
        return new ArrayList<>(activeRentals);
    }

    private ActiveRental findActiveRental(String rentalId) {
        return activeRentals.stream().filter(r -> r.getRentalId().equals(rentalId)).findFirst().orElse(null);
    }
}