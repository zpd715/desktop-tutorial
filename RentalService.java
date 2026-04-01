import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentalService {
    private List<ActiveRental> activeRentals;
    private BikeService bikeService;

    public RentalService(BikeService bikeService) {
        this.activeRentals = new ArrayList<>();
        this.bikeService = bikeService;
    }

    public boolean startRental(String userId, String bikeId) {
        // 检查自行车是否可用
        Bike bike = bikeService.findAvailableBikes("").stream()
                .filter(b -> b.getId().equals(bikeId)).findFirst().orElse(null);
        if (bike == null) {
            System.out.println("Bike not available.");
            return false;
        }
        // 保留自行车
        if (!bikeService.reserveBike(bikeId)) return false;

        String rentalId = UUID.randomUUID().toString();
        ActiveRental rental = new ActiveRental(rentalId, userId, bikeId, LocalDateTime.now());
        activeRentals.add(rental);
        System.out.println("Rental started: " + rentalId);
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
        // 释放自行车
        bikeService.releaseBike(rental.getBikeId());
        System.out.println("Rental ended: " + rentalId);
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