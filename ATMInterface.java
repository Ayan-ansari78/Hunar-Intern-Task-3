import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// User class representing each account holder
class User {
    private int accountNumber;
    private int pin;
    private double balance;

    public User(int accountNumber, int pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

// ATM class handling account management and banking operations
class ATM {
    private Map<Integer, User> accountDatabase;

    public ATM() {
        this.accountDatabase = new HashMap<>();
        // Example initialization with dummy data
        accountDatabase.put(123456, new User(123456, 1234, 1000.0));
        accountDatabase.put(987654, new User(987654, 4321, 500.0));
    }

    public boolean authenticateUser(int accountNumber, int pin) {
        User user = accountDatabase.get(accountNumber);
        return user != null && user.getPin() == pin;
    }

    public double checkBalance(int accountNumber) {
        User user = accountDatabase.get(accountNumber);
        return user.getBalance();
    }

    public void deposit(int accountNumber, double amount) {
        User user = accountDatabase.get(accountNumber);
        double newBalance = user.getBalance() + amount;
        user.setBalance(newBalance);
        System.out.println("Deposit successful. New balance: " + newBalance);
    }

    public boolean withdraw(int accountNumber, double amount) {
        User user = accountDatabase.get(accountNumber);
        if (amount > user.getBalance()) {
            System.out.println("Insufficient funds. Withdrawal failed.");
            return false;
        }
        double newBalance = user.getBalance() - amount;
        user.setBalance(newBalance);
        System.out.println("Withdrawal successful. New balance: " + newBalance);
        return true;
    }
}

// ATMInterface class handling user interaction
public class ATMInterface {
    private ATM atm;
    private Scanner scanner;

    public ATMInterface() {
        this.atm = new ATM();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to the ATM");
        while (true) {
            System.out.print("Enter your account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter your PIN: ");
            int pin = scanner.nextInt();

            if (atm.authenticateUser(accountNumber, pin)) {
                System.out.println("Authentication successful!");
                showMainMenu(accountNumber);
            } else {
                System.out.println("Authentication failed. Please try again.");
            }
        }
    }

    private void showMainMenu(int accountNumber) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    double balance = atm.checkBalance(accountNumber);
                    System.out.println("Your balance is: " + balance);
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(accountNumber, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    atm.withdraw(accountNumber, withdrawalAmount);
                    break;
                case 4:
                    System.out.println("Thank you for using our ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    public static void main(String[] args) {
        ATMInterface atmInterface = new ATMInterface();
        atmInterface.run();
    }
}
