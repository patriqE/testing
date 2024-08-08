import java.util.Scanner;

public class firstBank {

    int balance = 0;
    private int currentPin = 9992;


        Scanner input = new Scanner(System.in);

        public void checkBalance () {
            System.out.println("Your balance is " + balance);

        }
        public void deposit () {
            System.out.println("Enter the amount to deposit:");
            int deposited = input.nextInt();
            balance += deposited;
            System.out.println("Deposited " + deposited + " successfully");

        }
        public void withdraw () {
            System.out.println("Enter the amount to withdraw:");
            int withdrawn = input.nextInt();
            if (withdrawn > balance) {
                System.out.println("Insufficient balance");
            } else {
                balance -= withdrawn;
                System.out.println("Withdrawn " + withdrawn + " successfully");
                System.out.println("Your balance is :" + balance);
            }


        }

        public void transfer () {
            System.out.println("Enter the amount to transfer:");
            int transfered = input.nextInt();
            System.out.println("Enter the account number of the recipient:");
            int recipient = input.nextInt();
            if (transfered > balance) {
                System.out.println("Insufficient balance");
            } else {
                balance -= transfered;
                System.out.println("Transferred " + transfered + " to account number " + recipient + " successfully");
                System.out.println("Your balance is :" + balance);
            }

        }

    public void changePin() {
        System.out.println("Enter the old pin:");
        int oldPin = input.nextInt();
        if (oldPin == currentPin) {
            System.out.println("Enter the new pin:");
            int newPin = input.nextInt();
            System.out.println("Confirm the new pin:");
            int confirmPin = input.nextInt();
            while (newPin != confirmPin) {
                System.out.println("Pin confirmation failed");
                System.out.println("Enter the new pin:");
                newPin = input.nextInt();
                System.out.println("Confirm the new pin:");
                confirmPin = input.nextInt();
            }
            currentPin = newPin;
            System.out.println("Pin changed successfully");
        } else {
            System.out.println("Invalid pin");
        }
    }


    public void exit () {
            System.out.println("Thank you for using our banking service");
            System.exit(0);

        }
        public int getCurrentPin() {
            return currentPin;
        }
}

