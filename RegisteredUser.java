public class RegisteredUser {
    protected String fullName;
    protected String email;
    protected String phone;

    public RegisteredUser(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public double calculateFare(double baseFare) {
        return baseFare;   // 普通用户无折扣
    }

    public void displayUserType() {
        System.out.println("Regular User");
    }

    // Getters
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}