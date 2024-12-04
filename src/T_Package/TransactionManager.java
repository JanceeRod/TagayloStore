package T_Package;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static P_Package.paymentDefinitions.customerOrders;

public class TransactionManager {
    private final Map<String, Transaction> transactionMap;

    public TransactionManager(String CSVFilePath) {
        transactionMap = new TreeMap<>(Comparator.reverseOrder());
        loadTransactions(CSVFilePath);
    }

    public void loadTransactions(String transactionHistoryFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(transactionHistoryFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("TransactionId")) {
                    continue;  // Skip header line
                }

                String[] parts = line.split(",");
                System.out.println("Processing line: " + line);  // Debugging line

                if (parts.length != 5) {
                    System.out.println("Warning: Malformed line, expected 5 columns, found " + parts.length + " columns: " + line);
                    continue;
                }

                String transactionId = parts[0];
                String dateTime = parts[1];
                String purchaseId = parts[2];
                double cashAmount = 0.0;

                try {
                    cashAmount = Double.parseDouble(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid cash amount in line: " + line);
                    continue;  // Skip this line if cash amount is invalid
                }
                String customer = parts[4];

                // Parse LocalDateTime from the dateTime string
                LocalDateTime localDateTime = null;
                try {
                    localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    System.err.println("Invalid DateTime format in line: " + line);
                    continue;  // Skip this line if DateTime is invalid
                }

                // Create a new transaction
                Transaction transaction = new Transaction();
                transaction.setTransactionId(transactionId);
                transaction.setDateTime(localDateTime);
                transaction.setCashAmount(cashAmount);
                transaction.setCustomer(customer);
                transaction.setPurchaseId(purchaseId);

                // Add the transaction to the TreeMap, using LocalDateTime as the key
                transactionMap.put(String.valueOf(localDateTime), transaction);
                System.out.println("Transaction added to map: " + transactionId);  // Debugging line
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public double calculateGrandTotal(String transactionID, Map<String, Double> productPrices) {
        Transaction transaction = transactionMap.get(transactionID);
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction ID not found.");
        }

        String purchaseId = transaction.getPurchaseId();

        Map<String, Integer> purchases = getPurchases(purchaseId);

        double grandTotal = 0.0;
        for (Map.Entry<String, Integer> entry : purchases.entrySet()) {
            String productCode = entry.getKey();
            int quantity = entry.getValue();
            double price = productPrices.getOrDefault(productCode, 0.0);
            grandTotal += price * quantity;
        }

        return grandTotal;
    }


    public Map<String, Integer> getPurchases(String purchaseId) {
        Map<String, Integer> purchases = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("transactionPurchases.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    continue;
                }

                String currentPurchaseId = parts[0];
                String productCode = parts[1];
                int quantity = Integer.parseInt(parts[2]);

                if (currentPurchaseId.equals(purchaseId)) {
                    purchases.put(productCode, purchases.getOrDefault(productCode, 0) + quantity);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the purchase data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in quantity field: " + e.getMessage());
        }

        return purchases;
    }

    public String getPurchaseID(String transactionID) {
        Transaction transaction = transactionMap.get(transactionID);
        if (transaction != null) {
            return transaction.getPurchaseId();
        } else {
            return "Transaction ID not found.";
        }
    }

    public int getTransactionCount() {
        int size = transactionMap.size();

        System.out.println("Transaction Size: " + size);

        return size;
    }

    public String getTransactionIDByIndex(int index) {
        if (index < 0 || index >= transactionMap.size()) {
            return "Index out of bounds.";
        }

        int currentIndex = 0;
        for (String transactionID : transactionMap.keySet()) {
            if (currentIndex == index) {
                return transactionID;
            }
            currentIndex++;
        }
        return "Transaction not found.";
    }

    public String getPurchaseDate(String transactionID) {
        Transaction transaction = transactionMap.get(transactionID);
        if (transaction != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return transaction.getDateTime().format(formatter);
        } else {
            return "Transaction ID not found.";
        }
    }

    public double getPaidAmount(String transactionID) {
        Transaction transaction = transactionMap.get(transactionID);
        return (transaction != null) ? transaction.getCashAmount() : -1.0;
    }

    public String getCustomerName(String transactionID) {
        Transaction transaction = transactionMap.get(transactionID);
        return (transaction != null) ? transaction.getCustomer() : "Transaction ID not found.";
    }
}
