// 1.2.1 Creation of the account class
package components;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    
    public void setBalance(Flow flow, boolean effect) {
        double flowAmount = effect ? flow.getAmount() : -flow.getAmount();

        if (flow instanceof Transfer) {
            Transfer transfer = (Transfer) flow;
            if (transfer.getTargetAccountNumber() == this.getAccountNumber()) {
                this.balance += flowAmount;
            } else if (transfer.getIssuingAccountNumber() == this.getAccountNumber()) {
                this.balance -= flowAmount;
            }
        } else {
            this.balance += flowAmount;
        }
    }
    
    
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
