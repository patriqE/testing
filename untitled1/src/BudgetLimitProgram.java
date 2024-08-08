import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BudgetLimitProgram {
    private static final int INITIAL_BALANCE = 100000;
    private static final int WITHDRAWAL_LIMIT = 20000;
    private int currentBalance;
    private LocalDateTime lastWithdrawalTime;

    public BudgetLimitProgram() {
        this.currentBalance = INITIAL_BALANCE;
        this.lastWithdrawalTime = LocalDateTime.MIN; // Initialize with the minimum value
    }

    public void checkBalance() {
        System.out.println("Current Balance: $" + currentBalance);
    }

    public void deposit(int amount) {
        if (amount > 0) {
            currentBalance += amount;
            System.out.println("$" + amount + " deposited successfully.");
        } else {
            System.out.println("Invalid amount. Deposit amount must be greater than zero.");
        }
        checkBalance();
    }

    public void withdraw(int amount) {
        if (canWithdraw()) {
            if (amount > 0 && amount <= currentBalance && amount <= WITHDRAWAL_LIMIT) {
                currentBalance -= amount;
                lastWithdrawalTime = LocalDateTime.now();
                System.out.println("$" + amount + " withdrawn successfully.");
            } else {
                System.out.println("Withdrawal amount exceeds limit or insufficient balance.");
            }
        } else {
            long[] remainingTime = hoursAndMinutesUntilNextWithdrawal();
            System.out.println("Cannot withdraw yet. Try again in " + remainingTime[0] + " hours and " + remainingTime[1] + " minutes.");
        }
        checkBalance(); // Update balance display after any operation
    }

    private boolean canWithdraw() {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(lastWithdrawalTime, now).toHours() >= 24;
    }

    private long[] hoursAndMinutesUntilNextWithdrawal() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWithdrawalTime = lastWithdrawalTime.plusHours(24);
        long hours = Duration.between(now, nextWithdrawalTime).toHours();
        long minutes = Duration.between(now, nextWithdrawalTime).toMinutes() % 60;
        return new long[] { hours, minutes };
    }
}
