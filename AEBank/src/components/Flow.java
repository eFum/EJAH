// 1.3.2 Creation of the Flow class
package components;

import java.time.LocalDate;

public abstract class Flow {
	// Attributes
    private String comment;
    private int identifier;
    private double amount;
    private int targetAccountNumber;
    private boolean effect;
    private LocalDate date;

    // Constructor
    public Flow(String comment, double amount, int targetAccountNumber, boolean effect) {
        this.comment = comment;
        this.amount = amount;
        this.targetAccountNumber = targetAccountNumber;
        this.effect = effect;
        this.date = LocalDate.now().plusDays(2); // Operations are carried out 2 days after the creation of flows
    }
    
    public double getAmount() {
        return this.amount;
    }

    public int getTargetAccountNumber() {
        return this.targetAccountNumber;
    }
    
    public boolean getEffect(){
        return this.effect;
    }
    // Accessors and Mutators
    // (Include getters and setters for other attributes as needed)

}
