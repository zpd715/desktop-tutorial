import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserRegistration {
    public static final double VIP_DISCOUNT_UNDER_18_BIRTHDAY = 25.0;
    public static final double VIP_DISCOUNT_UNDER_18 = 20.0;
    public static final double VIP_BASE_FEE = 100.0;

    String fullName;
    String emailAddress;
    String dateOfBirth;
    long cardNumber;
    String cardProvider;
    String cardExpiryDate;
    double feeToCharge;
    int cvv;

    String userType;
    boolean emailValid;
    boolean minorAndBirthday;
    boolean minor;
    boolean ageValid;
    boolean cardNumberValid;
    boolean cardStillValid;
    boolean validCVV;

    @Override
    public String toString() {
        String cardNumberStr = String.valueOf(cardNumber);
        String censoredPart = "";
        if (cardNumberStr.length() > 4) {
            censoredPart = cardNumberStr.substring(0, cardNumberStr.length() - 4).replaceAll(".", "*");
        }
        String lastFourDigits = cardNumberStr.substring(cardNumberStr.length() - 4);
        String censoredNumber = censoredPart + lastFourDigits;

        return "Registration successful!\n" +
                "Here are your details:\n" +
                "User Type: " + userType + "\n" +
                "Full Name: " + fullName + "\n" +
                "Email Address: " + emailAddress + "\n" +
                "Date of Birth: " + dateOfBirth + "\n" +
                "Card Number: " + censoredNumber + "\n" +
                "Card Provider: " + cardProvider + "\n" +
                "Card Expiry Date: " + cardExpiryDate;
    }
}

class ERyderRegistration {
    private UserRegistration userReg = new UserRegistration();
    private Scanner scanner = new Scanner(System.in);

    public void registration() {
        System.out.println("Welcome to the ERyder Registration.");
        System.out.println("Here are your two options:");
        System.out.println("1. Register as a Regular User");
        System.out.println("2. Register as a VIP User");
        System.out.print("Please enter your choice (1 or 2): ");

        int choice = 0;
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine();
        } else {
            scanner.nextLine();
            System.out.println("Invalid choice! Please enter 1 or 2.");
            userReg.userType = "Unknown";
            registration();
            return;
        }

        if (choice == 1) {
            userReg.userType = "Regular User";
        } else if (choice == 2) {
            userReg.userType = "VIP User";
        } else {
            System.out.println("Invalid choice! Please enter 1 or 2.");
            userReg.userType = "Unknown";
            registration();
            return;
        }

        System.out.print("\nEnter your full name: ");
        userReg.fullName = scanner.nextLine();

        System.out.print("Enter your email address: ");
        userReg.emailAddress = scanner.nextLine();
        userReg.emailValid = analyseEmail(userReg.emailAddress);

        System.out.print("Enter your date of birth (YYYY-MM-DD): ");
        userReg.dateOfBirth = scanner.nextLine();
        LocalDate dob = null;
        try {
            dob = LocalDate.parse(userReg.dateOfBirth);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format!");
        }
        userReg.ageValid = analyseAge(dob);

        System.out.print("Enter your card number (Visa/MasterCard/American Express only): ");
        long cardNum = 0;
        if (scanner.hasNextLong()) {
            cardNum = scanner.nextLong();
            scanner.nextLine();
        } else {
            scanner.nextLine();
            System.out.println("Invalid card number!");
            userReg.cardNumberValid = false;
            registration();
            return;
        }
        userReg.cardNumber = cardNum;
        userReg.cardNumberValid = analyseCardNumber(userReg.cardNumber);

        System.out.print("Enter your card expiry date (MM/YY): ");
        userReg.cardExpiryDate = scanner.nextLine();
        userReg.cardStillValid = analyseCardExpiryDate(userReg.cardExpiryDate);

        System.out.print("Enter your card CVV: ");
        int cvv = 0;
        if (scanner.hasNextInt()) {
            cvv = scanner.nextInt();
        } else {
            scanner.nextLine();
            System.out.println("Invalid CVV!");
            userReg.validCVV = false;
            registration();
            return;
        }
        userReg.cvv = cvv;
        userReg.validCVV = analyseCVV(userReg.cvv);

        finalCheckpoint();
    }

    private boolean analyseEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            System.out.println("Email is valid");
            return true;
        } else {
            System.out.println("Invalid email address. Going back to the start of the registration");
            registration();
            return false;
        }
    }

    private boolean analyseAge(LocalDate dob) {
        if (dob == null) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);
        int age = period.getYears();

        boolean isBirthday = dob.getMonthValue() == currentDate.getMonthValue() &&
                dob.getDayOfMonth() == currentDate.getDayOfMonth();

        if (age <= 12 || age > 120) {
            System.out.println("Looks like you are either too young or already dead. Sorry, you can't be our user. Have a nice day");
            System.exit(0);
        }

        if ("VIP User".equals(userReg.userType)) {
            if (isBirthday && age <= 18 && age > 12) {
                System.out.println("Happy Birthday!\nYou get 25% discount on the VIP subscription fee for being born today and being under 18!");
                userReg.minorAndBirthday = true;
            } else if (!isBirthday && age <= 18 && age > 12) {
                System.out.println("You get 20% discount on the VIP subscription fee for being under 18!");
                userReg.minor = true;
            }
        }
        return true;
    }

    private boolean analyseCardNumber(long cardNumber) {
        String cardNumStr = String.valueOf(cardNumber);
        int firstTwoDigits = 0;
        if (cardNumStr.length() >= 2) {
            firstTwoDigits = Integer.parseInt(cardNumStr.substring(0, 2));
        }

        int firstFourDigits = 0;
        if (cardNumStr.length() >= 4) {
            firstFourDigits = Integer.parseInt(cardNumStr.substring(0, 4));
        }

        if ((cardNumStr.length() == 13 || cardNumStr.length() == 15) && cardNumStr.startsWith("4")) {
            userReg.cardProvider = "VISA";
            return true;
        } else if (cardNumStr.length() == 16 && ((firstTwoDigits >= 51 && firstTwoDigits <= 55) ||
                (firstFourDigits >= 2221 && firstFourDigits <= 2720))) {
            userReg.cardProvider = "MasterCard";
            return true;
        } else if (cardNumStr.length() == 15 && (cardNumStr.startsWith("34") || cardNumStr.startsWith("37"))) {
            userReg.cardProvider = "American Express";
            return true;
        } else {
            System.out.println("Sorry, but we accept only VISA, MasterCard, or American Express cards. Please try again with a valid card.\nGoing back to the start of the registration.");
            registration();
            return false;
        }
    }

    private boolean analyseCardExpiryDate(String cardExpiryDate) {
        int month = 0;
        int year = 0;
        try {
            month = Integer.parseInt(cardExpiryDate.substring(0, 2));
            year = Integer.parseInt(cardExpiryDate.substring(3, 5)) + 2000;
        } catch (Exception e) {
            System.out.println("Invalid expiry date format!");
            registration();
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        if (year > currentYear || (year == currentYear && month >= currentMonth)) {
            System.out.println("The card is still valid");
            return true;
        } else {
            System.out.println("Sorry, your card has expired. Please use a different card.\nGoing back to the start fo the registration process…");
            registration();
            return false;
        }
    }

    private boolean analyseCVV(int cvv) {
        String cvvStr = String.valueOf(cvv);
        if (("American Express".equals(userReg.cardProvider) && cvvStr.length() == 4) ||
                ("VISA".equals(userReg.cardProvider) && cvvStr.length() == 3) ||
                ("MasterCard".equals(userReg.cardProvider) && cvvStr.length() == 3)) {
            System.out.println("Card CVV is valid.");
            return true;
        } else {
            System.out.println("Invalid CVV for the given card.\nGoing back to the start of the registration process.");
            registration();
            return false;
        }
    }

    private void finalCheckpoint() {
        if (userReg.emailValid && userReg.ageValid && userReg.cardNumberValid && userReg.cardStillValid && userReg.validCVV) {
            chargeFees();
        } else {
            System.out.println("Sorry, your registration was unsuccessful due to the following reason(s)");
            if (!userReg.emailValid) System.out.println("Invalid email address");
            if (!userReg.ageValid) System.out.println("Invalid age");
            if (!userReg.cardNumberValid) System.out.println("Invalid card number");
            if (!userReg.cardStillValid) System.out.println("Card has expired");
            if (!userReg.validCVV) System.out.println("Invalid CVV");
            System.out.println("Going back to the start of the registration process.");
            registration();
        }
    }

    private void chargeFees() {
        if (userReg.minorAndBirthday) {
            userReg.feeToCharge = UserRegistration.VIP_BASE_FEE * (1 - UserRegistration.VIP_DISCOUNT_UNDER_18_BIRTHDAY / 100);
        } else if (userReg.minor) {
            userReg.feeToCharge = UserRegistration.VIP_BASE_FEE * (1 - UserRegistration.VIP_DISCOUNT_UNDER_18 / 100);
        } else {
            userReg.feeToCharge = UserRegistration.VIP_BASE_FEE;
        }

        String cardNumberStr = String.valueOf(userReg.cardNumber);
        String lastFourDigits = cardNumberStr.substring(cardNumberStr.length() - 4);
        System.out.println("Thank you for your payment.\nA fee of " + userReg.feeToCharge + " has been charged to your card ending with " + lastFourDigits);
    }
}

class Main {
    public static void main(String[] args) {
        ERyderRegistration registration = new ERyderRegistration();
        UserRegistration userReg = new UserRegistration();
        registration.registration();
        System.out.println(userReg);
    }
}