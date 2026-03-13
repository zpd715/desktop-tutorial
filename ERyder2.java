public class ERyder {

    public static final String COMPANY_NAME = "ERyder";  
    public static final double BASE_FARE = 1.0;          
    public static final double PER_MINUTE_FARE = 0.5;    

    private final String LINKED_ACCOUNT;        
    private final String LINKED_PHONE_NUMBER;  

    private String bikeID;          
    private int batteryLevel;         
    private boolean isAvailable;   
    private double kmDriven;         

    private int totalUsageInMinutes;  
    private double totalFare;        

    public ERyder(String bikeID, int batteryLevel, boolean isAvailable, double kmDriven) {
        this.bikeID = bikeID;
        this.setBatteryLevel(batteryLevel); 
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;

        this.LINKED_ACCOUNT = "default_user";
        this.LINKED_PHONE_NUMBER = "000-000-0000";

        this.totalUsageInMinutes = 0;
        this.totalFare = 0.0;
    }

    public ERyder(String bikeID, int batteryLevel, boolean isAvailable, double kmDriven,
                  String linkedAccount, String linkedPhoneNumber) {
        this.bikeID = bikeID;
        this.setBatteryLevel(batteryLevel);
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;

        this.LINKED_ACCOUNT = linkedAccount;
        this.LINKED_PHONE_NUMBER = linkedPhoneNumber;

        this.totalUsageInMinutes = 0;
        this.totalFare = 0.0;
    }

    public void ride() {
        if (this.batteryLevel > 0 && this.isAvailable) {
            System.out.println("Bike " + this.bikeID + " is available for riding.");
        } else {
            System.out.println("Bike " + this.bikeID + " is not available for riding.");
        }
    }

    public void printBikeDetails() {
        System.out.println("\n=== Bike Details ===");
        System.out.println("Bike ID: " + this.bikeID);
        System.out.println("Battery Level: " + this.batteryLevel + "%");
        System.out.println("Availability: " + (this.isAvailable ? "Available" : "Not Available"));
        System.out.println("Total Distance Driven: " + this.kmDriven + " km");
        System.out.println("====================");
    }

    public void printRideDetails(int usageInMinutes) {
        this.totalUsageInMinutes = usageInMinutes;
        this.totalFare = calculateFare(usageInMinutes);

        System.out.println("\n=== Ride Details ===");
        System.out.println("Linked Account: " + this.LINKED_ACCOUNT);
        System.out.println("Linked Phone Number: " + this.LINKED_PHONE_NUMBER);
        System.out.println("Bike ID: " + this.bikeID);
        System.out.println("Total Usage Time: " + usageInMinutes + " minutes");
        System.out.println("Total Fare: $" + this.totalFare);
        System.out.println("====================");
    }

    private double calculateFare(int usageInMinutes) {
        return BASE_FARE + (PER_MINUTE_FARE * usageInMinutes);
    }

    public String getBikeID() {
        return bikeID;
    }

    public void setBikeID(String bikeID) {
        this.bikeID = bikeID;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel;
        } else {
            System.out.println("Invalid battery level (" + batteryLevel + "). Must be 0-100. Setting to 0.");
            this.batteryLevel = 0;
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(double kmDriven) {
        this.kmDriven = kmDriven;
    }

    public int getTotalUsageInMinutes() {
        return totalUsageInMinutes;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public String getLINKED_ACCOUNT() {
        return LINKED_ACCOUNT;
    }

    public String getLINKED_PHONE_NUMBER() {
        return LINKED_PHONE_NUMBER;
    }
}

class ERyderMain {
    public static void main(String[] args) {）
        ERyder defaultBike = new ERyder("DEFAULT-000", 50, true, 75.0);
        defaultBike.printRideDetails(10);

        ERyder userBike = new ERyder("EB-12345", 80, true, 120.5, "john_doe", "123-456-7890");
        userBike.printRideDetails(20);
        System.out.println("\n--- About calculateFare() ---");
        System.out.println("1. Direct call to calculateFare() fails (it's private)");
        System.out.println("2. Proper way: Call it via a public method (e.g., printRideDetails())");
        System.out.println("   - printRideDetails() internally calls calculateFare()");
        System.out.println("   - Or add a public getter to access the calculated fare (e.g., getTotalFare())");
    }
}
