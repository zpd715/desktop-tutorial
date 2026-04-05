import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) {
        Deque<ERyderLog> systemLogStack = new ArrayDeque<>();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        BikeDatabase bikeDatabase = new BikeDatabase();
        BikeService bikeService = new BikeService(bikeDatabase, systemLogStack);
        RentalService rentalService = new RentalService(bikeService, systemLogStack);

        AdminPanel adminPanel = new AdminPanel(bikeService, rentalService, userService, systemLogStack);
        adminPanel.start();
    }
}