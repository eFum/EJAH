// 1.1.2 Creation of main class for test
package tests;

import components.Client;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
	
	// Method to generate an array of clients
    public static List<Client> generateClients(int numberOfClients) {
        List<Client> clients = new ArrayList<>();

        for (int i = 1; i <= numberOfClients; i++) {
            String name = "name" + i;
            String firstName = "firstname" + i;

            // Create and populate instances of the Client class
            Client client = new Client(firstName, name);
            clients.add(client);
        }

        return clients;
    }

    // Method to display the contents of the array using a stream
    public static void displayClients(List<Client> clients) {
        clients.stream().map(Client::toString).forEach(System.out::println);
    }

    public static void main(String[] args) {
        // Declare an array (Collection) of clients
        List<Client> clientList = generateClients(3); // Example: Generate 3 clients

        // Display the contents of the array using a stream
        displayClients(clientList);
    }
}
