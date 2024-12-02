package C_Package;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static A_Package.adminDefinitions.transactionHistory2D;

public class manageTransactions {
    private static String CSV_FILE_PATH = "transactionHistory.csv";
//    public static List<Transaction> transactionHistory = new ArrayList<>();

    public static Map<String, Object> getTransactionById(String filePath, String transactionId) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                Map<String, Object> transaction = parseTransaction(transactionHistory2D, line);

                if (transactionId.equals(transaction.get("transactionId"))) {
                    return transaction;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        return null;
    }

    public static Map<String, Object> parseTransaction(String[][] transactionHistory2D, String line) {
        String[] parts = line.split(",", 7);
        Map<String, Object> transaction = new HashMap<>();

        transaction.put("transactionId", parts[0]);
        transaction.put("dateTime", parts[1]);
        transaction.put("productsPurchased", parseProducts(parts[2]));
        transaction.put("totalAmount", Double.parseDouble(parts[3]));
        transaction.put("cashAmount", Double.parseDouble(parts[4]));
        transaction.put("change", Double.parseDouble(parts[5]));
        transaction.put("customer", parts[6]);

        return transaction;
    }

    private static List<Map<String, Object>> parseProducts(String productsString) {
        List<Map<String, Object>> products = new ArrayList<>();

        // Clean and split the products string
        productsString = productsString.substring(2, productsString.length() - 2);
        String[] productEntries = productsString.split("\\], \\[");

        for (String productEntry : productEntries) {
            String[] productDetails = productEntry.split(", ");
            Map<String, Object> product = new HashMap<>();
            product.put("productCode", productDetails[0]);
            product.put("quantity", Integer.parseInt(productDetails[1]));
            products.add(product);
        }

        return products;
    }

    public manageTransactions(String CSV_FILE_PATH) {
        manageTransactions.CSV_FILE_PATH = CSV_FILE_PATH;
    }

    public String[][] readCSVToArray() {
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
    
}
