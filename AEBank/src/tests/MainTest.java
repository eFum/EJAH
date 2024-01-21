// 1.1.2 Creation of main class for test
package tests;

import components.Account;
import components.Client;
import components.CurrentAccount;
import components.SavingsAccount;

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
    //1.2.3 Creation of the table account
    // Method to display the contents of the array using a stream
    public static void displayClients(List<Client> clients) {
        clients.stream().map(Client::toString).forEach(System.out::println);
    }
    
    // Method to generate an array of accounts based on the array of clients
    public static List<Account> generateAccounts(List<Client> clients) {
        List<Account> accounts = new ArrayList<>();

        for (Client client : clients) {
            // Generate a savings account and a current account per client with balances at zero
            SavingsAccount savingsAccount = new SavingsAccount("Savings", client);
            CurrentAccount currentAccount = new CurrentAccount("Current", client);

            accounts.add(savingsAccount);
            accounts.add(currentAccount);
        }

        return accounts;
    }

    // Method to display the contents of the array using a stream
    public static void displayAccounts(List<Account> accounts) {
        accounts.stream().map(Account::toString).forEach(System.out::println);
    }

    public static void main(String[] args) {
        // Declare an array (Collection) of clients
        List<Client> clientList = generateClients(3); // Example: Generate 3 clients
        // Generate an array of accounts based on the array of clients
        List<Account> accountList = generateAccounts(clientList);

        // Display the contents of the array using a stream
        displayClients(clientList);
        // Display the contents of the array of accounts using a stream
        displayAccounts(accountList);
    }
}
