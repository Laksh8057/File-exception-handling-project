import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
class InvalidAmountException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidAmountException(String message) {
        super(message);
    }
}
class InsufficientFundsException extends Exception {
    private static final long serialVersionUID = 1L;
    public InsufficientFundsException(String message) {
        super(message);
    }
}
class BankAccount {
    private String accountHolderName;
    private int accountNumber;
    private double balance;
    public BankAccount(String name) {
        this.accountHolderName = name;
        this.accountNumber = new Random().nextInt(9000) + 1000; // Random 4-digit account number
        this.balance = 0;
    }
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        balance += amount;
        System.out.println("Deposited: $" + amount);
    }
    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }
        balance -= amount;
        System.out.println("Withdrawn: $" + amount);
    }
    public double getBalance() {
        return balance;
    }

    public String getAccountInfo() {
        return "Account Holder: " + accountHolderName + "\nAccount Number: " + accountNumber + "\nBalance: $" + String.format("%.2f", balance);
    }
}
public class BANK {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = null;
        try {
            System.out.println("Welcome to the Online Bank Account Management System!");
            System.out.print("Please enter your name to create a new account: ");
            String name = scanner.nextLine();
            account = new BankAccount(name);
            System.out.println("Account created successfully!");
            System.out.println(account.getAccountInfo());
            boolean running = true;
            while (running) {
                System.out.println("\n=== Bank Account Menu ===");
                System.out.println("1. Deposit Money");
                System.out.println("2. Withdraw Money");
                System.out.println("3. View Balance and Account Info");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = getValidChoice(scanner);
                switch (choice) {
                    case 1:
                        depositProcess(account, scanner);
                        break;
                    case 2:
                        withdrawProcess(account, scanner);
                        break;
                    case 3:
                        System.out.println(account.getAccountInfo());
                        break;
                    case 4:
                        running = false;
                        System.out.println("Exiting... Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1-4.");
                }
            }
        } finally {
            scanner.close();
            System.out.println("Scanner closed.");
        }
    }
    private static void depositProcess(BankAccount account, Scanner scanner) {
        boolean depositing = true;
        while (depositing) {
            try {
                System.out.print("Enter amount to deposit: ");
                double amount = scanner.nextDouble();
                account.deposit(amount);
            } catch (InputMismatchException e) {
                System.out.println("Input error: Please enter a numeric value.");
                scanner.next();
            } catch (InvalidAmountException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.print("Do you want to deposit more? (y/n): ");
            String answer = scanner.next();
            depositing = answer.equalsIgnoreCase("y");
        }
    }
    private static void withdrawProcess(BankAccount account, Scanner scanner) {
        boolean withdrawing = true;
        while (withdrawing) {
            try {
                System.out.print("Enter amount to withdraw: ");
                double amount = scanner.nextDouble();
                account.withdraw(amount);
            } catch (InputMismatchException e) {
                System.out.println("Input error: Please enter a numeric value.");
                scanner.next();
            } catch (InvalidAmountException | InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.print("Do you want to withdraw more? (y/n): ");
            String answer = scanner.next();
            withdrawing = answer.equalsIgnoreCase("y");
        }
    }
    private static int getValidChoice(Scanner scanner) {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Input error: Please enter a valid menu option.");
            scanner.next();
        }
        return choice;
    }
}