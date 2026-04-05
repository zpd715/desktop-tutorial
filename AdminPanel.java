import java.util.*;

public class AdminPanel {
    private BikeService bikeService;
    private RentalService rentalService;
    private UserService userService;
    private Deque<ERyderLog> systemLogStack;

    public AdminPanel(BikeService bikeService, RentalService rentalService, UserService userService,
                      Deque<ERyderLog> systemLogStack) {
        this.bikeService = bikeService;
        this.rentalService = rentalService;
        this.userService = userService;
        this.systemLogStack = systemLogStack;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    handleUserRegistration(scanner);
                    break;
                case "2":
                    handleFindBikes(scanner);
                    break;
                case "3":
                    handleStartRental(scanner);
                    break;
                case "4":
                    handleEndRental(scanner);
                    break;
                case "5":
                    handleTrackRentals();
                    break;
                case "6":
                    userService.displayUsers();
                    break;
                case "7":
                    handleRemoveUser(scanner);
                    break;
                case "8":
                    viewSystemLogs();
                    break;
                case "9":
                    managePendingRequests(scanner);
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== ERyder System ===");
        System.out.println("1. Register new user");
        System.out.println("2. Find available bikes by location");
        System.out.println("3. Start rental");
        System.out.println("4. End rental");
        System.out.println("5. Track active rentals");
        System.out.println("6. List all users");
        System.out.println("7. Remove user");
        System.out.println("8. View System Logs");
        System.out.println("9. Manage Pending Bike Requests");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleUserRegistration(Scanner scanner) {
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter user type (VIP/Regular): ");
        String type = scanner.nextLine();
        userService.addUser(fullName, email, phone, type);
    }

    private void handleFindBikes(Scanner scanner) {
        System.out.print("Enter location (Downtown/Uptown): ");
        String location = scanner.nextLine();
        if (!bikeService.validateLocation(location)) {
            System.out.println("Invalid location.");
            return;
        }
        List<Bike> bikes = bikeService.findAvailableBikes(location);
        if (bikes.isEmpty()) {
            System.out.println("No bikes available at " + location);
        } else {
            System.out.println("Available bikes:");
            bikes.forEach(System.out::println);
        }
    }

    private void handleStartRental(Scanner scanner) {
        System.out.print("Enter user email: ");
        String email = scanner.nextLine();
        RegisteredUser user = userService.findUserByEmail(email);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.print("Enter bike ID: ");
        String bikeId = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        rentalService.startRental(user, bikeId, location);
    }

    private void handleEndRental(Scanner scanner) {
        System.out.print("Enter rental ID: ");
        String rentalId = scanner.nextLine();
        rentalService.endRental(rentalId);
    }

    private void handleTrackRentals() {
        List<ActiveRental> rentals = rentalService.trackActiveRentals();
        if (rentals.isEmpty()) {
            System.out.println("No active rentals.");
        } else {
            System.out.println("Active rentals:");
            rentals.forEach(System.out::println);
        }
    }

    private void handleRemoveUser(Scanner scanner) {
        System.out.print("Enter user email to remove: ");
        String email = scanner.nextLine();
        if (userService.removeUser(email)) {
            System.out.println("User removed.");
        } else {
            System.out.println("User not found.");
        }
    }

    private void viewSystemLogs() {
        if (systemLogStack.isEmpty()) {
            System.out.println("No system logs available.");
        } else {
            System.out.println("\n=== System Logs ===");
            for (ERyderLog log : systemLogStack) {
                System.out.println(log);
            }
        }
    }

    private void managePendingRequests(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Pending Bike Requests ---");
            System.out.println("1. View Queue");
            System.out.println("2. Update Queue (remove first)");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    Queue<BikeRequest> queue = bikeService.getBikeRequestQueue();
                    if (queue.isEmpty()) {
                        System.out.println("Queue is empty.");
                    } else {
                        System.out.println("Pending requests:");
                        for (BikeRequest req : queue) {
                            System.out.println(req);
                        }
                    }
                    break;
                case "2":
                    bikeService.removeFirstRequest();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}