// 1.1.2 Creation of main class for test
package tests;

import components.Account;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.SavingsAccount;
import components.Transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    // 1.2.3 Creation of the table account
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
    
    // 1.3.1 Adaptation of the table of accounts
    // Method to create a Hashtable (account identifier, Account) of accounts
    public static Hashtable<Integer, Account> loadAccountTable(List<Account> accounts) {
        Hashtable<Integer, Account> accountTable = new Hashtable<>();
        for (Account account : accounts) {
            accountTable.put(account.getAccountNumber(), account);
        }
        return accountTable;
    }

    // Method to display the Map in ascending order according to the balance
    public static void displayAccountTable(Map<Integer, Account> accountMap) {
        accountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((account1, account2) -> Double.compare(account1.getBalance(), account2.getBalance())))
                .forEach(entry -> System.out.println("Account Id: " + entry.getKey() + ", " + entry.getValue().toString()));
    }
    
    // 1.3.4 Creation of the flow array
    // Method to create an array of flows
    public static List<Flow> generateFlows(Hashtable<Integer, Account> accountTable) {
        List<Flow> flows = new ArrayList<>();
        flows.add(new Debit("Debit", 50, 1, false));
        for (Account account : accountTable.values()) {
            if (account instanceof CurrentAccount) {
                flows.add(new Credit("Credit", 100.50, account.getAccountNumber(), false));
            } else if (account instanceof SavingsAccount) {
                flows.add(new Credit("Credit", 1500, account.getAccountNumber(), false));
            }
        }
        flows.add(new Transfer("Transfer", 50, 2, 1));
        return flows;
    }
    
    // 1.3.5 Updating accounts
    // Method to update balances of accounts and check for negative balances
    public static void updateBalances(List<Flow> flows, Map<Integer, Account> accountMap) {
        flows.forEach(flow -> {
            Account account = accountMap.get(flow.getTargetAccountNumber());

            if (account != null) {
                account.setBalance(flow, flow.getEffect());
            }
        });

        // Check for negative balance using Optional and Predicate
        accountMap.values().stream()
            .filter(a -> a.getBalance() < 0)
            .forEach(a -> System.out.println("Account with negative balance: " + a.toString()));
    }


    public static void main(String[] args) {
    	System.out.println("Generating Clients :\n");
        // Declare an array (Collection) of clients
        List<Client> clientList = generateClients(3);
        // Display the contents of the array using a stream
        displayClients(clientList);
        System.out.println("____________________\n");
        
        System.out.println("Generating Accounts :\n");
        // Generate an array of accounts based on the array of clients
        List<Account> accountList = generateAccounts(clientList);
        // Display the contents of the array of accounts using a stream
        displayAccounts(accountList);
        System.out.println("____________________\n");
        
        System.out.println("Updating Balances :\n");
        // Create a Hashtable (account identifier, Account) of accounts
        Hashtable<Integer, Account> accountTable = loadAccountTable(accountList);
        // Generate an array of flows
        List<Flow> flowList = generateFlows(accountTable);
        // Update balances of accounts and check for negative balances
        updateBalances(flowList, accountTable);
        System.out.println("____________________\n");
        System.out.println("Displaying Accounts :\n");
        // Display the Map in ascending order according to the balance
        displayAccountTable(accountTable);
        System.out.println("____________________\n");
        
    }
}
