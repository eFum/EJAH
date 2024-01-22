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
        this.setComment(comment);
        this.amount = amount;
        this.targetAccountNumber = targetAccountNumber;
        this.effect = effect;
        // Operations are carried out 2 days after the creation of flows
        this.setDate(LocalDate.now().plusDays(2));
    }
    
    // Accessors and Mutators
    public double getAmount() {
        return this.amount;
    }

    public int getTargetAccountNumber() {
        return this.targetAccountNumber;
    }
    
    public boolean getEffect(){
        return this.effect;
    }

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	// toString method
    @Override
    public String toString() {
        return "Flow{" +
               "comment='" + comment + '\'' +
               ", identifier=" + identifier +
               ", amount=" + amount +
               ", targetAccountNumber=" + targetAccountNumber +
               ", effect=" + effect +
               ", date=" + date +
               '}';
    }
}
