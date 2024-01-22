// 1.1.2 Creation of main class for test
package tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import components.Account;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.SavingsAccount;
import components.Transfer;

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
    // Method to display the contents of the Clients array using a stream
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

    // Method to display the contents of the Accounts array using a stream
    public static void displayAccounts(List<Account> accounts) {
        accounts.stream().map(Account::toString).forEach(System.out::println);
    }
    
    // Method to display the contents of the Flows array using a stream
    public static void displayFlows(List<Flow> flows) {
        flows.stream().map(Flow::toString).forEach(System.out::println);
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

    // Method to display the Hashtable in ascending order according to the balance
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
        Optional<Account> negativeBalanceAccount = accountMap.values().stream()
                .filter(a -> a.getBalance() < 0)
                .findAny();

        negativeBalanceAccount.ifPresent(a -> System.out.println("Account with negative balance found : " + a.toString()));
    }
    
    // 2.1 JSON file of flows
    // Method to load a file (JSON format) and return a filled-in Flow array
    public static List<Flow> loadFlowsFromJsonFile(String filename, List<Account> accounts) {
        List<Flow> flows = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String type = jsonObject.getString("type");
                double amount = jsonObject.getDouble("amount");

                if (jsonObject.get("targetAccountNumber") instanceof Integer) {
                    int targetAccountNumber = jsonObject.getInt("targetAccountNumber");

                    Flow flow;
                    if (type.equals("Debit")) {
                        flow = new Debit("Debit", amount, targetAccountNumber, true);
                    } else if (type.equals("Credit")) {
                        flow = new Credit("Credit", amount, targetAccountNumber, true);
                    } else { // Transfer
                        int issuingAccountNumber = jsonObject.getInt("issuingAccountNumber");
                        flow = new Transfer("Transfer", amount, targetAccountNumber, issuingAccountNumber);
                    }

                    flows.add(flow);
                } else {
                    // Handle the case where targetAccountNumber is a string
                    String targetAccountType = jsonObject.getString("targetAccountNumber");
                    for (Account account : accounts) {
                        if ((account instanceof CurrentAccount && targetAccountType.equals("all current accounts"))
                            || (account instanceof SavingsAccount && targetAccountType.equals("all savings accounts"))) {
                            Flow flow = new Credit("Credit", amount, account.getAccountNumber(), true);
                            flows.add(flow);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flows;
    }


    // 2.1 JSON file of flows
    // Method to load a file (XML format) and return a filled-in Accounts array
    public static List<Account> loadAccountsFromXmlFile(String filename) {
        List<Account> accounts = new ArrayList<>();

        try {
            File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("account");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String label = element.getElementsByTagName("label").item(0).getTextContent();
                    double balance = Double.parseDouble(element.getElementsByTagName("balance").item(0).getTextContent());
                    int accountNumber = Integer.parseInt(element.getElementsByTagName("accountNumber").item(0).getTextContent());

                    Element clientElement = (Element) element.getElementsByTagName("client").item(0);
                    String firstName = clientElement.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = clientElement.getElementsByTagName("lastName").item(0).getTextContent();
                    int clientNumber = Integer.parseInt(clientElement.getElementsByTagName("clientNumber").item(0).getTextContent());

                    Client client = new Client(firstName, lastName);
                    client.setClientNumber(clientNumber);

                    Account account;
                    if (label.equals("Savings")) {
                        account = new SavingsAccount(label, client);
                    } else { // Current
                        account = new CurrentAccount(label, client);
                    }
                    account.setBalance(balance);
                    account.setAccountNumber(accountNumber);

                    accounts.add(account);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
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
        
        System.out.println("Loaded XML Accounts :\n");
        // Load accounts from an XML file
        List<Account> accounts = loadAccountsFromXmlFile("accounts.xml");
        // Display of the loaded accounts
        displayAccounts(accounts);
        System.out.println("____________________\n");
        
        System.out.println("Loaded JSON Flows :\n");
        // Load flows from a JSON file
        List<Flow> flows = loadFlowsFromJsonFile("flows.json", accounts);
        // Display of the loaded flows
        displayFlows(flows);
        System.out.println("____________________\n");
    }
}
