import java.util.Scanner;

public class AdminPanel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Admin Panel =====");
            System.out.println("1. View all bikes");
            System.out.println("2. Demo the Bike Rental System");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Bike Database ---");
                    for (Bike bike : BikeDatabase.bikes) {
                        System.out.println(bike);
                    }
                    break;
                case 2:
                    System.out.println("\n--- Starting Bike Rental Demo ---");
                    BikeRental rentalSystem = new BikeRental();
                    rentalSystem.simulateApplication();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting Admin Panel.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}