import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BudgetLimitProgram budgetProgram = new BudgetLimitProgram();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    budgetProgram.checkBalance();
                    break;
                case 2:
                    System.out.println("Enter deposit amount:");
                    int depositAmount = scanner.nextInt();
                    budgetProgram.deposit(depositAmount);
                    break;
                case 3:
                    System.out.println("Enter withdrawal amount:");
                    int withdrawAmount = scanner.nextInt();
                    budgetProgram.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}