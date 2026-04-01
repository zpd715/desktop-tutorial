import java.util.List;

public class UserService {
    private RegisteredUsers registeredUsers;
    private UserRegistration userRegistration;

    public UserService(RegisteredUsers registeredUsers, UserRegistration userRegistration) {
        this.registeredUsers = registeredUsers;
        this.userRegistration = userRegistration;
    }

    public boolean addUser(String username, String password) {
        return userRegistration.registerUser(username, password);
    }

    public boolean removeUser(String username) {
        if (registeredUsers.containsUser(username)) {
            registeredUsers.removeUser(username);
            System.out.println("User " + username + " removed.");
            return true;
        }
        System.out.println("User not found.");
        return false;
    }

    public boolean updateUser(String oldUsername, String newUsername, String newPassword) {
        User user = registeredUsers.findUser(oldUsername);
        if (user == null) {
            System.out.println("User not found.");
            return false;
        }
        // 简单更新：删除原用户再添加新用户
        registeredUsers.removeUser(oldUsername);
        registeredUsers.addUser(new User(newUsername, newPassword));
        System.out.println("User updated from " + oldUsername + " to " + newUsername);
        return true;
    }

    public List<User> getAllUsers() {
        return registeredUsers.getAllUsers();
    }

    public void displayUsers() {
        List<User> users = getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users registered.");
        } else {
            System.out.println("Registered Users:");
            users.forEach(u -> System.out.println(" - " + u.getUsername()));
        }
    }
}