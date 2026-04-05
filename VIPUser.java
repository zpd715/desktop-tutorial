public class VIPUser extends RegisteredUser {
    public VIPUser(String fullName, String email, String phone) {
        super(fullName, email, phone);
    }

    @Override
    public double calculateFare(double baseFare) {
        return baseFare * 0.8;   // 20%折扣
    }

    @Override
    public void displayUserType() {
        System.out.println("VIP User");
    }
}