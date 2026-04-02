import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) {
        // 创建全局系统日志栈
        Deque<ERyderLog> systemLogStack = new ArrayDeque<>();

        // 初始化依赖
        RegisteredUsers registeredUsers = new RegisteredUsers();
        UserRegistration userRegistration = new UserRegistration(registeredUsers);
        BikeDatabase bikeDatabase = new BikeDatabase();
        BikeService bikeService = new BikeService(bikeDatabase, systemLogStack);
        RentalService rentalService = new RentalService(bikeService, systemLogStack);
        UserService userService = new UserService(registeredUsers, userRegistration);

        AdminPanel adminPanel = new AdminPanel(bikeService, rentalService, userService, systemLogStack);
        adminPanel.start();
    }
}