import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        firstBank login = new firstBank();
        int defaultPassword = 9992;
        int attempts = 3;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to First Bank!");

        while (attempts > 0) {
            System.out.println("Please enter your password:");
            int pin = scanner.nextInt();

            if (pin == defaultPassword) {
                System.out.println("Password is correct. Welcome!");
                // Add your menu options here
                break;
            } else {
                attempts--;
                if (attempts > 0) {
                    System.out.println("Incorrect password. You have " + attempts + " more attempts.");
                } else {
                    System.out.println("Incorrect password. Access denied!.");
                }
            }
        }

        if (attempts == 0) {
            System.out.println("You have been locked out due to too many incorrect attempts.");

        } else {
            boolean running = true;
            while (running) {
                System.out.println("Welcome to your account!");
                System.out.println("Please select an option:");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. Logout");
                System.out.println("6. Change pin");

                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        login.checkBalance();
                        break;
                    case 2:
                        login.deposit();
                        break;
                    case 3:
                        login.withdraw();
                        break;
                    case 4:
                        login.transfer();
                        break;
                    case 5:
                        System.out.println("You have been logged out.");
                        login.exit();
                        running = false;
                        break;
                    case 6:
                        login.changePin();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }

                int pinAttempts = 3;
                boolean pinVerified = false;
                while (pinAttempts > 0 && !pinVerified) {

                    System.out.println("Please enter your current PIN to continue:");
                    int currentPin = scanner.nextInt();
                    if (currentPin == login.getCurrentPin()) {
                        pinVerified = true;
                    } else {
                        pinAttempts--;
                        if (pinAttempts > 0) {
                            System.out.println("Incorrect PIN. You have " + pinAttempts + " more attempts.");
                        } else {
                            System.out.println("Incorrect PIN. ATM card blocked.");
                            login.exit();
                            running = false;
                        }
                    }
                }
            }
        }
    }
}
