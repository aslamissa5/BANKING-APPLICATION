// GROUP NINE
//GUMONYE ASLAM 26/U/03507/PSA 2600703507
//SSEWAKILYANGA DESIRE 26/U/0361/PSA 2600703651
//ANIRWOTH DANIELLA 26/U/03485/PS 2600703485
//AKORIO JOSEPH 26/U/30958/PS 2600730958
//KIKOBYE JUDITH 26/U/03534/PS 2600703534
//BANKING APPLICATION
//this helps us create Buttons, textboxes, labels, windows,(so the JFrame, JButton and JTextfield all come from here instead of making imports for each)
import javax.swing.*; 
import java.awt.*;// this enables the Layouts(How things are arranged)e.g.Borderlayout, Gridlayout, FlowLayout.
import java.awt.event.*;//this enables events to occur(What happens when a button is clicked)e.g.ActionListener
import java.util.ArrayList;//Creates a list that can grow and helps us store all the accounts.

abstract class Account {// This is the Blueprint. It is abstract because a plain Account should never exist in real life only savings and current
    // and the abstract class helps us share common code, and treat all accounts as one type
    private String accountNumber;
    private String holderName;
    private double balance;
    private String pin;

    public Account(String accountNumber, String holderName, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    //this checks the typed PIN to the PIN you used to create the account
    public boolean checkPin(String enteredPin) { return this.pin.equals(enteredPin); }
    public void deposit(double amount) { if(amount > 0) balance += amount; }
    //Both methods below are abstract in order to force different rules where needed
    public abstract boolean withdraw(double amount);
    public abstract String getAccountType();
}

class SavingsAccount extends Account {
    public SavingsAccount(String accNo, String name, double bal, String pin) { super(accNo, name, bal, pin); }
    @Override
    public boolean withdraw(double amount) {
        if (getBalance() - amount >= 100) { super.deposit(-amount); return true; }//must atleast leave 100 in order to withdraw
        return false;
    }
    @Override
    public String getAccountType() { return "Savings"; }
}

class CurrentAccount extends Account {
    public CurrentAccount(String accNo, String name, double bal, String pin) { super(accNo, name, bal, pin); }
    @Override// here you can withdraw any amount of money
    public boolean withdraw(double amount) {
        if (getBalance() >= amount) { super.deposit(-amount); return true; } 
        return false;
    }
    @Override
    public String getAccountType() { return "Current"; }
}

class Bank {//this the filling cabinet, helps us store accounts
    private ArrayList<Account> accounts = new ArrayList<>();// this is the list that holds all the accouts
    public void addAccount(Account acc) { accounts.add(acc); }// When an account is created, it goes onto the  list
    //this methode hekps us to loop through the lists one by one and returns if the account is found and if not found returns null
    public Account findAccount(String accNo) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNo)) return acc;
        }
        return null;
    }
    public ArrayList<Account> getAllAccounts() { return accounts; }
}
//This makes a window using BankingApp instead of creating one from scratch just use JFrame's window and add stuff inside
public class BankingApp extends JFrame {
    //this creates one empty filling cabinet.We need it for the whole life of the app, so we create it here once, not inside a method that will disappear
    private Bank bank = new Bank();
    private JTextArea outputArea;
    //we declare them here, but don't fill them yet. We just say: These boxes exist and all methods in the class can see them.
    private JTextField txtAccNo, txtName, txtBalance, txtAmount, txtTransferTo;
    private JPasswordField txtPin;

    public BankingApp() {//This runs once when we create new BankingApp() in main. it helps build the empty window
        setTitle("OOP BANKING APPLICATION");// The words that appear very top of the window
        setSize(600, 550);// makes our window 600 pixels wide, and 550 pixels tall
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//When you click x, the whole program stops. Without this, the window closes but Java keeps running in the background
        setLayout(new BorderLayout());//helps us arrange things 

        // 1. INPUT - 6 rows, 2 columns
        //JPanel = An empty plate. You put things on it, then you put the whole tray in the window. We don't add labels directly to the window
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));// this creates 6 rows and 2 columns and the (5, 5) is the pixel gap btn the text fields
        inputPanel.setBorder(BorderFactory.createTitledBorder("Account Details"));//Draws a box around the tray with the words "Account Details" on top.
         
        //this helps us fill the trays 
        // JTextfield = Normal box, we see what we type
        inputPanel.add(new JLabel("Account No:"));
        txtAccNo = new JTextField(); inputPanel.add(txtAccNo);

        inputPanel.add(new JLabel("Holder Name:"));
        txtName = new JTextField(); inputPanel.add(txtName);

        inputPanel.add(new JLabel("Initial Balance:"));
        txtBalance = new JTextField(); inputPanel.add(txtBalance);

        inputPanel.add(new JLabel("PIN (4 digits):"));
        //JPasswordField = Special box for PIN, It shows .... instead of numbers. It hides your PIN from someone
        txtPin = new JPasswordField(); inputPanel.add(txtPin);

        inputPanel.add(new JLabel("Amount:"));
        txtAmount = new JTextField(); inputPanel.add(txtAmount);

        inputPanel.add(new JLabel("Transfer To Acc No:"));
        txtTransferTo = new JTextField(); inputPanel.add(txtTransferTo);

        // 2. BUTTONS
        // FlowLayout = Just put things in a line, left to right, to and if they don't fit, wrap to the next line, like words in a paragraph. Perfect for buttons.
        JPanel btnPanel = new JPanel(new FlowLayout());
        // JButton = A clickable button. The text inside. is what the user sees on the button.
        JButton btnCreate = new JButton("Create Savings");
        JButton btnCreateCurrent = new JButton("Create Current");
        JButton btnDeposit = new JButton("Deposit");
        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnCheck = new JButton("Check Balance");
        JButton btnList = new JButton("List All");
        JButton btnTransfer = new JButton("Transfer");

        // helps us put all our buttons on this tray, in the order we want them to appear
        btnPanel.add(btnCreate); btnPanel.add(btnCreateCurrent);
        btnPanel.add(btnDeposit); btnPanel.add(btnWithdraw);
        btnPanel.add(btnCheck); btnPanel.add(btnTransfer); btnPanel.add(btnList);

        // 3. OUTPUT
        //JTextArea = A big multi-line box, like a notebook page. This is where messages like "Created Account..." It's different from JTextField which is only line.
        outputArea = new JTextArea(7, 20);
        //user cannot type in it. It's read-only. only the program can write to it using. If we didn't do this, the user could delete the messages.
        outputArea.setEditable(false);
        // A scrollable frame around the text Area. When you create many accounts, the messages go past the bottom window. Without it messages would disappear.
        // With it, a scrollbar appears on the right so you can scroll up and down.
        JScrollPane scroll = new JScrollPane(outputArea);

        // SIMPLE ADD - NO mainPanel, NO setPreferredSize
        add(inputPanel, BorderLayout.NORTH);// Puts input tray to the NORTH
        add(btnPanel, BorderLayout.CENTER);// Puts button tray to the middle (CENTRE)
        add(scroll, BorderLayout.SOUTH);//Puts output scroll at the Bottom.

        // this means "When someone clicks this button, do this"
        //e -> is a short way of saying "when the event happens"
        btnCreate.addActionListener(e -> createAccount(true));
        btnCreateCurrent.addActionListener(e -> createAccount(false));
        btnDeposit.addActionListener(e -> deposit());
        btnWithdraw.addActionListener(e -> withdraw());
        btnCheck.addActionListener(e -> checkBalance());
        btnList.addActionListener(e -> listAccounts());
        btnTransfer.addActionListener(e -> transfer());
    }

    private String getPin() { return new String(txtPin.getPassword()); }

    
    private boolean isValidNumber(String text) {
        if (text == null || text.isEmpty()) return false;
        boolean hasDot = false; boolean hasDigit = false;
        for (int i = 0; i < text.length(); i++) {// loops through every character
            char c = text.charAt(i);
            if (i == 0 && (c == '-' || c == '+')) continue;
            if (c == '.') { if (hasDot) return false; hasDot = true; continue; }
            if (c >= '0' && c <= '9') { hasDigit = true; continue; }
            return false;
        }
        return hasDigit;
    }
    //trim() removes spaces. So if we type " 332  " it helps us remove the spaces and puts it back to normal "332"
    private boolean isEmpty(String text) { return text == null || text.trim().isEmpty(); }

    private void createAccount(boolean isSavings) {
        String accNo = txtAccNo.getText().trim();//get text from boxes, remove spaces
        String name = txtName.getText().trim();
        String balText = txtBalance.getText().trim();
        String pin = getPin();
        if (isEmpty(accNo)) { output("Error: Enter Account No"); return; }
        if (isEmpty(name)) { output("Error: Enter Holder Name"); return; }
        if (!isValidNumber(balText)) { output("Error: Enter valid Balance - numbers only"); return; }
        if (pin.isEmpty()) { output("Error: Please enter a PIN"); return; }
        if (pin.length() != 4) { output("Error: PIN must be 4 digits"); return; }
        if (bank.findAccount(accNo) != null) { output("Account already exists!"); return; }
        double bal = Double.parseDouble(balText);//convert "1000"(text) to 1000.0(number)
        if (bal < 0) { output("Error: Balance cannot be negative"); return; }
        Account acc = isSavings ? new SavingsAccount(accNo, name, bal, pin) : new CurrentAccount(accNo, name, bal, pin);
        bank.addAccount(acc);
        output("Created " + acc.getAccountType() + " Account: " + accNo + " with PIN set");
    }

    private void deposit() {
        String accNo = txtAccNo.getText().trim();
        String amtText = txtAmount.getText().trim();
        if (isEmpty(accNo)) { output("Enter Account No"); return; }
        if (!isValidNumber(amtText)) { output("Error: Enter valid Amount"); return; }
        Account acc = bank.findAccount(accNo);
        if (acc != null) {
            if (!acc.checkPin(getPin())) { output("Incorrect PIN - Deposit denied"); return; }
            double amt = Double.parseDouble(amtText);
            if (amt <= 0) { output("Error: Amount must be > 0"); return; }
            acc.deposit(amt);
            output("Deposited " + amt + " to " + acc.getAccountNumber() + ". New Bal: " + acc.getBalance());
        } else output("Account not found");
    }

    private void withdraw() {
        String accNo = txtAccNo.getText().trim();
        String amtText = txtAmount.getText().trim();
        if (isEmpty(accNo)) { output("Enter Account No"); return; }
        if (!isValidNumber(amtText)) { output("Error: Enter valid Amount"); return; }
        Account acc = bank.findAccount(accNo);
        if (acc != null) {
            if (!acc.checkPin(getPin())) { output("Incorrect PIN - Withdraw denied"); return; }
            double amt = Double.parseDouble(amtText);
            if (amt <= 0) { output("Error: Amount must be > 0"); return; }
            if (acc.withdraw(amt)) output("Withdrawn " + amt + ". New Bal: " + acc.getBalance());
            else output("Withdraw failed - Insufficient balance / min balance rule");
        } else output("Account not found");
    }

    private void checkBalance() {
        String accNo = txtAccNo.getText().trim();
        if (isEmpty(accNo)) { output("Enter Account No"); return; }
        Account acc = bank.findAccount(accNo);
        if (acc != null) {
            if (!acc.checkPin(getPin())) { output("Incorrect PIN - Cannot show balance"); return; }
            output(acc.getAccountNumber() + " | " + acc.getHolderName() + " | " + acc.getAccountType() + " | Balance: " + acc.getBalance());
        } else output("Account not found");
    }

    private void listAccounts() {
        output("--- ALL ACCOUNTS ---");
        for (Account acc : bank.getAllAccounts()) {
            output(acc.getAccountNumber() + " - " + acc.getHolderName() + " - " + acc.getAccountType() + " - " + acc.getBalance());
        }
    }

    private void transfer() {
        String fromNo = txtAccNo.getText().trim();
        String toNo = txtTransferTo.getText().trim();
        String amtText = txtAmount.getText().trim();
        if (isEmpty(fromNo) || isEmpty(toNo)) { output("Enter both Account Numbers"); return; }
        if (!isValidNumber(amtText)) { output("Error: Enter valid Amount"); return; }
        Account from = bank.findAccount(fromNo);
        Account to = bank.findAccount(toNo);
        if (from != null && to != null) {
            if (!from.checkPin(getPin())) { output("Incorrect PIN - Transfer denied"); return; }
            double amt = Double.parseDouble(amtText);
            if (amt <= 0) { output("Error: Amount must be > 0"); return; }
            if (from.withdraw(amt)) { to.deposit(amt); output("Transferred " + amt + " from " + from.getAccountNumber() + " to " + to.getAccountNumber()); }
            else output("Transfer failed - insufficient funds");
        } else output("One of the accounts not found");
    }

    private void output(String s) { outputArea.append(s + "\n"); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankingApp().setVisible(true));//creating a new bankingApp window and show it
        //the invokelater just makes sure the window is created safely on the correct thread
    }
}