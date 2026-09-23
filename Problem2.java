import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numAccounts = 0;
        while (true) {
            System.out.print("Enter number of bank accounts (1 to 5): ");
            if (scanner.hasNextInt()) {
                numAccounts = scanner.nextInt();
                if (numAccounts >= 1 && numAccounts <= 5) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid number. Must be between 1 and 5.");
        }
        scanner.nextLine();

        BankAccount[] accounts = new BankAccount[numAccounts];

        for (int i = 0; i < numAccounts; i++) {
            System.out.println("\n--- Account " + (i + 1) + " ---");
            System.out.print("Enter Account Number: ");
            String accNum = scanner.next();

            scanner.nextLine();
            System.out.print("Enter Owner Name: ");
            String owner = scanner.nextLine().trim();

            double balance = -1;
            while (balance < 0) {
                System.out.print("Enter Opening Balance: ");
                if (scanner.hasNextDouble()) {
                    balance = scanner.nextDouble();
                    if (balance < 0) {
                        System.out.println("Opening balance cannot be negative.");
                    }
                } else {
                    scanner.next();
                    System.out.println("Invalid input. Enter a valid positive number.");
                }
            }
            scanner.nextLine();

            accounts[i] = new BankAccount(accNum, owner, balance);
        }

        System.out.print("\nEnter number of transactions to process: ");
        int numTx = scanner.nextInt();

        for (int t = 1; t <= numTx; t++) {
            System.out.println("\n--- Transaction " + t + " ---");
            System.out.print("Enter Account Number: ");
            String targetAcc = scanner.next();
            System.out.print("Enter Transaction Type (D for Deposit / W for Withdrawal): ");
            char type = scanner.next().toUpperCase().charAt(0);
            System.out.print("Enter Amount: ");
            double amount = scanner.nextDouble();

            BankAccount target = null;
            for (BankAccount acc : accounts) {
                if (acc.getAccountNumber().equalsIgnoreCase(targetAcc)) {
                    target = acc;
                    break;
                }
            }

            if (target == null) {
                System.out.println("Transaction REJECTED: Account number " + targetAcc + " not found.");
            } else if (type == 'D') {
                if (target.deposit(amount)) {
                    System.out.printf("Transaction SUCCESS: Deposited PHP %.2f to Account %s.%n", amount, targetAcc);
                } else {
                    System.out.println("Transaction REJECTED: Deposit amount must be positive.");
                }
            } else if (type == 'W') {
                if (target.withdraw(amount)) {
                    System.out.printf("Transaction SUCCESS: Withdrew PHP %.2f from Account %s.%n", amount, targetAcc);
                } else {
                    System.out.println("Transaction REJECTED: Insufficient funds or invalid withdrawal amount.");
                }
            } else {
                System.out.println("Transaction REJECTED: Invalid transaction type.");
            }
        }

        System.out.println("\n================ FINAL ACCOUNT BALANCES ================");
        System.out.printf("%-15s %-20s %-15s%n", "Account No.", "Owner Name", "Balance");
        System.out.println("-------------------------------------------------------");
        for (BankAccount acc : accounts) {
            System.out.printf("%-15s %-20s PHP %-10.2f%n", acc.getAccountNumber(), acc.getOwnerName(), acc.getBalance());
        }
        System.out.println("=======================================================");
        System.out.println("Total Account Objects Created: " + BankAccount.getAccountCount());

        scanner.close();
    }
}

class BankAccount {
    private String accountNumber;
    private String ownerName;
    private double balance;
    private static int accountCount = 0;

    public BankAccount(String accountNumber, String ownerName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance >= 0 ? initialBalance : 0;
        accountCount++;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public double getBalance() {
        return this.balance;
    }

    public static int getAccountCount() {
        return accountCount;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > this.balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }
}
