import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class BikeRental {
    private boolean isRegisteredUser;
    private String emailAddress;
    private String location;
    private LocalDateTime tripStartTime;
    private ArrayList<ActiveRental> activeRentalsList;

    public BikeRental() {
        activeRentalsList = new ArrayList<>();
    }

    public void simulateApplication() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Are you a registered user? (true/false): ");
        isRegisteredUser = scanner.nextBoolean();
        scanner.nextLine();

        System.out.print("Enter your email address: ");
        emailAddress = scanner.nextLine();

        System.out.print("Enter the location where you want to rent a bike: ");
        location = scanner.nextLine();

        String bikeID = analyseRequest();

        if (bikeID != null) {
            reserveBike(bikeID);
        } else {
            System.out.println("No bike available. Please try again later.");
        }

        System.out.println("\n--- Current Active Rentals ---");
        viewActiveRentals();

        System.out.print("\nDo you want to end a trip? Enter bike ID (or 'none'): ");
        String endId = scanner.nextLine();
        if (!endId.equalsIgnoreCase("none")) {
            removeTrip(endId);
        }

        System.out.println("\n--- Updated Active Rentals ---");
        viewActiveRentals();
    }

    private String analyseRequest() {
        if (isRegisteredUser) {
            System.out.println("Welcome back, " + emailAddress + "!");
        } else {
            System.out.println("You're not our registered user. Please consider registering.");
            UserRegistration.registration();
        }
        return validateLocation(location);
    }

    private String validateLocation(String location) {
        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getLocation().equals(location) && bike.isAvailable()) {
                System.out.println("A bike is available at the location you requested.");
                return bike.getBikeID();
            }
        }
        System.out.println("Sorry, no bikes are available at the location you requested. Please try again later.");
        return null;
    }

    private void reserveBike(String bikeID) {
        if (bikeID == null) {
            System.out.println("Sorry, we're unable to reserve a bike at this time. Please try again later.");
            return;
        }

        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getBikeID().equals(bikeID)) {
                tripStartTime = LocalDateTime.now();
                bike.setAvailable(false);
                bike.setLastUsedTime(tripStartTime);

                System.out.println("Reserving the bike with the " + bikeID +
                        ". Please follow the on-screen instructions to locate the bike and start your pleasant journey.");

                ActiveRental rental = new ActiveRental(bikeID, emailAddress, tripStartTime);
                activeRentalsList.add(rental);
                break;
            }
        }
    }

    private void viewActiveRentals() {
        if (activeRentalsList.isEmpty()) {
            System.out.println("No active rentals at the moment.");
        } else {
            for (ActiveRental rental : activeRentalsList) {
                System.out.println(rental);
            }
        }
    }

    private void removeTrip(String bikeID) {
        Iterator<ActiveRental> iterator = activeRentalsList.iterator();
        while (iterator.hasNext()) {
            ActiveRental rental = iterator.next();
            if (rental.getBikeID().equals(bikeID)) {
                iterator.remove();
                System.out.println("Your trip has ended. Thank you for riding with us.");
                break;
            }
        }

        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getBikeID().equals(bikeID)) {
                bike.setAvailable(true);
                bike.setLastUsedTime(LocalDateTime.now());
                break;
            }
        }
    }
}