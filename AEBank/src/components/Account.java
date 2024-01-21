//1.2.1 Creation of the account class
package components;

public abstract class Account {
	// Attributes
    protected String label;
    protected double balance;
    protected int accountNumber;
    protected Client client;

    // Static variable to track the total number of accounts
    protected static int totalAccounts = 0;

    // Constructor
    public Account(String label, Client client) {
        this.label = label;
        this.client = client;
        this.accountNumber = ++totalAccounts;
    }

    // Accessors and Mutators
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getBalance() {
        return balance;
    }
/*
    public void setBalance(double amount, FlowType flowType) {
        // Modify balance based on the type of flow (transfer, credit, debit)
        switch (flowType) {
            case TRANSFER:
                // Logic for transfer
                break;
            case CREDIT:
                // Logic for credit
                break;
            case DEBIT:
                // Logic for debit
                break;
            // Add more cases as needed
        }
    }
*/
    public int getAccountNumber() {
        return accountNumber;
    }

    public Client getClient() {
        return client;
    }

    // toString method
    @Override
    public String toString() {
        return "Account{" +
               "label='" + label + '\'' +
               ", balance=" + balance +
               ", accountNumber=" + accountNumber +
               ", client=" + client +
               '}';
    }
}
