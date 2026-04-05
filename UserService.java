import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegisteredUser addUser(String fullName, String email, String phone, String userType) {
        RegisteredUser newUser;
        if (userType.equalsIgnoreCase("VIP")) {
            newUser = new VIPUser(fullName, email, phone);
        } else {
            newUser = new RegularUser(fullName, email, phone);
        }
        userRepository.addUser(newUser);
        System.out.println("User " + fullName + " registered as " + userType);
        return newUser;
    }

    public boolean removeUser(String email) {
        return userRepository.removeUser(email);
    }

    public RegisteredUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<RegisteredUser> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void displayUsers() {
        List<RegisteredUser> users = getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users registered.");
        } else {
            System.out.println("Registered Users:");
            for (RegisteredUser u : users) {
                System.out.print(" - " + u.getFullName() + " (" + u.getEmail() + ") -> ");
                u.displayUserType();
            }
        }
    }
}