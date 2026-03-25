import java.util.*;

public class AdminPanel {
    private List<RegisteredUsers> registeredUsersList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void userManagementOptions() {
        int choice;
        do {
            System.out.println("\n=== Admin Panel ===");
            System.out.println("1. Add New Users");
            System.out.println("2. View Registered Users");
            System.out.println("3. Remove Registered Users");
            System.out.println("4. Update Registered Users");
            System.out.println("5. EXIT");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewUsers();
                    break;
                case 2:
                    viewRegisteredUsers();
                    break;
                case 3:
                    removeRegisteredUsers();
                    break;
                case 4:
                    updateRegisteredUsers();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void addNewUsers() {
        System.out.print("How many users would you like to add? ");
        int numUsers = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numUsers; i++) {
            System.out.println("\n--- Adding User " + (i + 1) + " ---");

            System.out.print("Full Name: ");
            String fullName = scanner.nextLine();

            System.out.print("Email Address: ");
            String emailAddress = scanner.nextLine();

            System.out.print("Date of Birth (YYYY-MM-DD): ");
            String dateOfBirth = scanner.nextLine();

            System.out.print("Card Number: ");
            String cardNumber = scanner.nextLine();

            System.out.print("Card Provider: ");
            String cardProvider = scanner.nextLine();

            System.out.print("Card Expiry Date (MM/YY): ");
            String cardExpiryDate = scanner.nextLine();

            System.out.print("CVV: ");
            String cvv = scanner.nextLine();

            System.out.print("User Type: ");
            String userType = scanner.nextLine();

            String[] lastThreeTrips = new String[3];
            for (int tripIndex = 0; tripIndex < 3; tripIndex++) {
                System.out.println("\n--- Trip " + (tripIndex + 1) + " ---");
                System.out.print("Date (YYYY-MM-DD): ");
                String tripDate = scanner.nextLine();

                System.out.print("Source: ");
                String source = scanner.nextLine();

                System.out.print("Destination: ");
                String destination = scanner.nextLine();

                System.out.print("Fare (€): ");
                double fare = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("Feedback (can be NULL): ");
                String feedback = scanner.nextLine();

                StringBuilder tripBuilder = new StringBuilder();
                tripBuilder.append("Date: ").append(tripDate)
                        .append(", Source: ").append(source)
                        .append(", Destination: ").append(destination)
                        .append(", Fare (€): ").append(fare)
                        .append(", Feedback: ").append(feedback);

                lastThreeTrips[tripIndex] = tripBuilder.toString();
            }

            RegisteredUsers user = new RegisteredUsers(fullName, emailAddress, dateOfBirth,
                    cardNumber, cardExpiryDate, cardProvider, cvv, userType, lastThreeTrips);
            registeredUsersList.add(user);
        }
        System.out.println(numUsers + " user(s) added successfully.");
    }

    private void viewRegisteredUsers() {
        if (registeredUsersList.isEmpty()) {
            System.out.println("No registered users to display.");
            return;
        }

        System.out.println("\n=== Registered Users ===");
        for (RegisteredUsers user : registeredUsersList) {
            System.out.println(user);
        }
    }

    private void removeRegisteredUsers() {
        if (registeredUsersList.isEmpty()) {
            System.out.println("No registered users to remove.");
            return;
        }

        System.out.print("Enter the email address of the user to remove: ");
        String email = scanner.nextLine();

        Iterator<RegisteredUsers> iterator = registeredUsersList.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            RegisteredUsers user = iterator.next();
            if (user.getEmailAddress().equals(email)) {
                iterator.remove();
                found = true;
                System.out.println("User with email " + email + " removed.");
                break;
            }
        }
        if (!found) {
            System.out.println("No user found with this email address.");
        }
    }

    private void updateRegisteredUsers() {
        if (registeredUsersList.isEmpty()) {
            System.out.println("No registered users to update.");
            return;
        }

        System.out.print("Enter the email address of the user to update: ");
        String email = scanner.nextLine();

        RegisteredUsers userToUpdate = null;
        for (RegisteredUsers user : registeredUsersList) {
            if (user.getEmailAddress().equals(email)) {
                userToUpdate = user;
                break;
            }
        }

        if (userToUpdate == null) {
            System.out.println("No user found with this email address.");
            return;
        }

        System.out.println("\nUpdating user: " + userToUpdate.getFullName());
        System.out.println("(Press ENTER to keep the current value, or enter '0' for card number to keep it)");

        System.out.print("Type new full name (press ENTER for no change): ");
        String newFullName = scanner.nextLine();
        if (!newFullName.isEmpty()) {
            userToUpdate.setFullName(newFullName);
        }

        System.out.print("Type new email address (press ENTER for no change): ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) {
            userToUpdate.setEmailAddress(newEmail);
        }

        System.out.print("Type new date of birth (YYYY-MM-DD) (press ENTER for no change): ");
        String newDob = scanner.nextLine();
        if (!newDob.isEmpty()) {
            userToUpdate.setDateOfBirth(newDob);
        }

        System.out.print("Type new card number (enter '0' for no change): ");
        String newCardNumber = scanner.nextLine();
        if (!newCardNumber.equals("0")) {
            userToUpdate.setCardNumber(newCardNumber);
        }

        System.out.print("Type new card expiry date (MM/YY) (press ENTER for no change): ");
        String newExpiry = scanner.nextLine();
        if (!newExpiry.isEmpty()) {
            userToUpdate.setCardExpiryDate(newExpiry);
        }

        System.out.print("Type new card provider (press ENTER for no change): ");
        String newProvider = scanner.nextLine();
        if (!newProvider.isEmpty()) {
            userToUpdate.setCardProvider(newProvider);
        }

        System.out.print("Type new CVV (press ENTER for no change): ");
        String newCvv = scanner.nextLine();
        if (!newCvv.isEmpty()) {
            userToUpdate.setCvv(newCvv);
        }

        System.out.print("Type new user type (press ENTER for no change): ");
        String newUserType = scanner.nextLine();
        if (!newUserType.isEmpty()) {
            userToUpdate.setUserType(newUserType);
        }

        System.out.println("User details updated successfully.");
    }
}