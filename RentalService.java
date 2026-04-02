import java.time.LocalDateTime;
import java.util.*;

public class RentalService {
    private List<ActiveRental> activeRentals;
    private BikeService bikeService;
    private Deque<ERyderLog> systemLogStack;

    public RentalService(BikeService bikeService, Deque<ERyderLog> systemLogStack) {
        this.activeRentals = new ArrayList<>();
        this.bikeService = bikeService;
        this.systemLogStack = systemLogStack;
    }

    public boolean startRental(String userId, String bikeId, String location) {
        // 注意：原reserveBike需要三个参数，这里需要传递userId和location
        if (!bikeService.reserveBike(bikeId, userId, location)) {
            return false;
        }

        String rentalId = UUID.randomUUID().toString();
        ActiveRental rental = new ActiveRental(rentalId, userId, bikeId, LocalDateTime.now());
        activeRentals.add(rental);
        System.out.println("Rental started: " + rentalId);

        // 记录日志：行程开始
        String logId = "TS" + System.currentTimeMillis();
        ERyderLog log = new ERyderLog(logId, "Trip started for user " + userId + " with bike " + bikeId, LocalDateTime.now());
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
        activeRentals.remove(rental);
        bikeService.releaseBike(rental.getBikeId());
        System.out.println("Rental ended: " + rentalId);

        // 记录日志：行程结束
        String logId = "TE" + System.currentTimeMillis();
        ERyderLog log = new ERyderLog(logId, "Trip ended for user " + rental.getUserId() + " with bike " + rental.getBikeId(), LocalDateTime.now());
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