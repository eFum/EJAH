// Creation of the Transfert class
package components;

public class Transfer extends Flow {
    // Additional attribute
    private int issuingAccountNumber;

    // Constructor
    public Transfer(String comment, double amount, int targetAccountNumber, int issuingAccountNumber) {
        super(comment, amount, targetAccountNumber, true);
        this.issuingAccountNumber = issuingAccountNumber;
    }
    
    public int getIssuingAccountNumber() {
        return this.issuingAccountNumber;
    }

    // Accessors and Mutators
    // (Include getters and setters for additional attributes as needed)
}
