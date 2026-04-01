import java.util.List;

public class BikeService {
    private BikeDatabase bikeDatabase;

    public BikeService(BikeDatabase bikeDatabase) {
        this.bikeDatabase = bikeDatabase;
    }

    public List<Bike> findAvailableBikes(String location) {
        return bikeDatabase.findAvailableBikesByLocation(location);
    }

    public boolean validateLocation(String location) {
        return location != null && !location.trim().isEmpty();
    }

    public boolean reserveBike(String bikeId) {
        Bike bike = bikeDatabase.findBikeById(bikeId);
        if (bike != null && bike.isAvailable()) {
            bike.setAvailable(false);
            bikeDatabase.updateBike(bike);
            System.out.println("Bike " + bikeId + " reserved.");
            return true;
        }
        System.out.println("Bike not available or not found.");
        return false;
    }

    public boolean releaseBike(String bikeId) {
        Bike bike = bikeDatabase.findBikeById(bikeId);
        if (bike != null && !bike.isAvailable()) {
            bike.setAvailable(true);
            bikeDatabase.updateBike(bike);
            System.out.println("Bike " + bikeId + " released.");
            return true;
        }
        System.out.println("Bike not in use or not found.");
        return false;
    }
}