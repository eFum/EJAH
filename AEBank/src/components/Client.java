// 1.1.1 Creation of the client class
package components;

public class Client {
	// Attributes
    private String firstName;
    private String lastName;
    private int clientNumber;

    // Static variable to track the total number of clients
    private static int totalClients = 0;

    // Constructor
    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientNumber = ++totalClients;
    }

    // Accessors and Mutators
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    // toString method
    @Override
    public String toString() {
        return "Client{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", clientNumber=" + clientNumber +
               '}';
    }
}
