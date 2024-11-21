package C_Package;

import E_Package.Category;
import E_Package.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class manageTransactions {
    private static String CSV_FILE_PATH = "transactionHistory.csv";
    public static List<Transaction> transactionHistory = new ArrayList<>();

    public manageTransactions(String CSV_FILE_PATH) {
        manageTransactions.CSV_FILE_PATH = CSV_FILE_PATH;
    }

    public String[][] readCsvToArray() {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        String[][] array = new String[data.size()][];
        return data.toArray(array);
    }

    public void printArray(String[][] array) {
        for (String[] row : array) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        manageTransactions transactionsManager = new manageTransactions(CSV_FILE_PATH);

        String[][] transactions = transactionsManager.readCsvToArray();
        transactionsManager.printArray(transactions);
    }
    
}
