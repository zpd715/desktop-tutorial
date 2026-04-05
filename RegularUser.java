public class RegularUser extends RegisteredUser {
    public RegularUser(String fullName, String email, String phone) {
        super(fullName, email, phone);
    }

    @Override
    public double calculateFare(double baseFare) {
        return super.calculateFare(baseFare); // 与父类相同
    }

    @Override
    public void displayUserType() {
        System.out.println("Regular User");
    }
}