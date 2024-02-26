
import java.util.Scanner;

// Transaction class to represent individual transactions
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    // Getter methods
    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

// Account class to manage user's account details and transactions
class Account {
    private String userId;
    private String pin;
    private String name; // New field for user's name
    private double balance;
    private Transaction[] transactions;
    private int transactionCount;

    public Account(String userId, String pin, String name) {
        this.userId = userId;
        this.pin = pin;
        this.name = name;
        this.balance = 1500.0; // Initial balance set to $1500
        this.transactions = new Transaction[100]; // Assuming a maximum of 100 transactions
        this.transactionCount = 0;
    }

    //  to authenticate user
    public boolean authenticate(String userId, String pin) {
        return this.userId.equals(userId) && this.pin.equals(pin);
    }

    //  to change PIN (password)
    public void changePin(String newPin) {
        this.pin = newPin;
        System.out.println("PIN changed successfully.");
    }

    //  to deposit money into the account
    public void deposit(double amount) {
        balance += amount;
        transactions[transactionCount++] = new Transaction("Deposit", amount);
    }

    //  to withdraw money from the account
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions[transactionCount++] = new Transaction("Withdrawal", amount);
            return true;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }

    // to transfer money to another account
    public boolean transfer(double amount, Account recipient) {
        if (withdraw(amount)) {
            recipient.updateBalance(amount);
            recipient.deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    // Method to display transaction history
    public void displayTransactionHistory() {
        System.out.println("Transaction History for " + name + " (" + userId + "):");
        for (int i = 0; i < transactionCount; i++) {
            Transaction transaction = transactions[i];
            System.out.println(transaction.getType() + ": RS." + transaction.getAmount());
        }
    }

    //  to check current balance
    public double checkBalance() {
        return balance;
    }

    //  to update balance during transfer
    private void updateBalance(double amount) {
        balance += amount;
    }

    // Getter method for balance
    public double getBalance() {
        return balance;
    }

    // Getter method for user ID
    public String getUserId() {
        return userId;
    }

    // Getter method for user's name
    public String getName() {
        return name;
    }
}

// ATM class to manage ATM operations and user interactions
class ATM {
    private Account[] accounts;

    public ATM(Account[] accounts) {
        this.accounts = accounts;
    }

    // Method to display ATM menu
    public void displayMenu() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║               ATM MENU                ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1. View Transactions History          ║");
        System.out.println("║ 2. Withdraw                           ║");
        System.out.println("║ 3. Deposit                            ║");
        System.out.println("║ 4. Transfer                           ║");
        System.out.println("║ 5. Check Balance                      ║");
        System.out.println("║ 6. Change PIN                         ║");
        System.out.println("║ 7. Quit                               ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }

    // to handle user input and execute selected operation
    public void performOperation(int choice, Scanner scanner, String userId) {
        Account account = findAccount(userId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        switch (choice) {
            case 1:
                account.displayTransactionHistory();
                break;
            case 2:
                System.out.print("Enter amount to withdraw: ");
                double withdrawAmount = scanner.nextDouble();
                account.withdraw(withdrawAmount);
                break;
            case 3:
                System.out.print("Enter amount to deposit: ");
                double depositAmount = scanner.nextDouble();
                account.deposit(depositAmount);
                break;
            case 4:
                System.out.print("Enter recipient's user ID: ");
                String recipientId = scanner.next();
                Account recipient = findAccount(recipientId);
                if (recipient != null) {
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    if (account.transfer(transferAmount, recipient)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed. Insufficient balance.");
                    }
                } else {
                    System.out.println("Recipient account not found.");
                }
                break;
            case 5:
                double balance = account.checkBalance();
                System.out.println("Your current balance is: RS." + balance);
                break;
            case 6:
                changePin(scanner, account);
                break;
            case 7:
                System.out.println("Exiting ATM. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // to handle changing PIN
    private void changePin(Scanner scanner, Account account) {
        System.out.print("Enter current PIN: ");
        String currentPin = scanner.next();
        if (account.authenticate(account.getUserId(), currentPin)) {
            System.out.print("Enter new PIN: ");
            String newPin = scanner.next();
            System.out.print("Confirm new PIN: ");
            String confirmPin = scanner.next();
            if (newPin.equals(confirmPin)) {
                account.changePin(newPin);
            } else {
                System.out.println("PINs do not match. Please try again.");
            }
        } else {
            System.out.println("Authentication failed. PIN remains unchanged.");
        }
    }

    // to find account by user ID
    private Account findAccount(String userId) {
        for (Account account : accounts) {
            if (account.getUserId().equals(userId)) {
                return account;
            }
        }
        return null;
    }
}

public class Bank {
    public static void main(String[] args) {
        // Create multiple user accounts with initial balance of $1500
        Account[] accounts = new Account[25];
        accounts[0] = new Account("user123", "1234", "Akash_Mondal");
        accounts[1] = new Account("user124", "5678", "Rounak_Chaterjee");
        accounts[2] = new Account("user125", "9012", "Sagnik_Banerjee");
        accounts[3] = new Account("user126", "9012", "Aniket_Pal");
        accounts[4] = new Account("user127", "9012", "Arpan_Sasmal");
        accounts[5] = new Account("user128", "9012", "Anish_Naskar");
        accounts[6] = new Account("user129", "9012", "Parambrata_Chaterjee");
        accounts[7] = new Account("user130", "9012", "Soumya_Mondal");
        accounts[8] = new Account("user131", "9012", "Sagnik_Manna");
        accounts[9] = new Account("user132", "9012", "Subhayu_Mandal");
        accounts[10] = new Account("user133", "9012", "Stabak_Das");
        accounts[11] = new Account("user134", "9012", "Souvik_Nath");
        accounts[12] = new Account("user135", "9012", "Somnath_Pal");
        accounts[13] = new Account("user136", "9012", "Sauptik_MUkherjee");
        accounts[14] = new Account("user137", "9012", "Mohini_Hazra");
        accounts[15] = new Account("user138", "9012", "Debasmita_Kar");
        accounts[16] = new Account("user139", "9012", "Debdeep_bhai");
        accounts[17] = new Account("user140", "9012", "Utsab_Mondal");
        accounts[18] = new Account("user141", "9012", "Dwaipayan_Banerjee");
        accounts[19] = new Account("user142", "9012", "Abhilash_Shina");
        accounts[20] = new Account("user143", "9012", "Aritra_Mukherjee");
        accounts[21] = new Account("user144", "9012", "Rupam_Chaterjee");
        accounts[22] = new Account("user145", "9012", "Roshni_Das");
        accounts[23] = new Account("user146", "9012", "Prattoy_Dey");
        accounts[24] = new Account("user147", "9012", "Sumana_Pal");
        // Create ATM instance with multiple accounts
        ATM atm = new ATM(accounts);
        
        // user input 
        Scanner scanner = new Scanner(System.in);
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║           WELCOME TO THE ATM          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Enter your user ID: ");
        String inputUserId = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String inputPin = scanner.nextLine();
        
        // Authenticate user and get user's name
        String userName = "";
        for (Account account : accounts) {
            if (account.authenticate(inputUserId, inputPin)) {
                userName = account.getName();
                break;
            }
        }
        
        if (!userName.isEmpty()) {
            System.out.println("Welcome=> " + userName + "!");
            int choice;
            do {
                atm.displayMenu();
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                atm.performOperation(choice, scanner, inputUserId);
            } while (choice != 7);
        } else {
            System.out.println("Authentication failed. Please try again.");
        }
        scanner.close();
    }
}
