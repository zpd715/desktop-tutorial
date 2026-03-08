
public class ERyder {

    private String bikeID;      
    private int batteryLevel;    
    private boolean isAvailable; 
    private double kmDriven;    

  
    public ERyder() {

        this.bikeID = "DEFAULT-000";
        this.batteryLevel = 0;
        this.isAvailable = false;
        this.kmDriven = 0.0;
    }

    public ERyder(String bikeID, int batteryLevel, boolean isAvailable, double kmDriven) {
        this.bikeID = bikeID;
        this.setBatteryLevel(batteryLevel);
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;
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
}

class Main {
    public static void main(String[] args) {
        ERyder defaultBike = new ERyder();
        System.out.println("--- Default Constructor Bike ---");
        defaultBike.printBikeDetails();

        ERyder customBike = new ERyder("EB-12345", 80, true, 120.5);
        System.out.println("\n--- Parameterized Constructor Bike ---");
        customBike.ride();
        customBike.printBikeDetails();

        ERyder testBike = new ERyder("TEST-001", 150, false, 50.0); 
        testBike.printBikeDetails();
    }
}