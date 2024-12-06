package T_Package;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static P_Package.paymentDefinitions.customerOrders;

public class TransactionManager {
    private static Map<String, Transaction> transactionMap = new LinkedHashMap<>();

    public TransactionManager(String CSVFilePath) {

        loadTransactions(CSVFilePath);

    }

    public void loadTransactions(String transactionHistoryFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(transactionHistoryFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("TransactionId")) {
                    continue;
                }

                String[] parts = line.split(",");
                System.out.println("Processing line: " + line);

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
                    continue;
                }
                String customer = parts[4];

                LocalDateTime localDateTime = null;
                try {
                    localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    System.err.println("Invalid DateTime format in line: " + line);
                    continue;
                }

                Transaction transaction = new Transaction();
                transaction.setTransactionId(transactionId);
                transaction.setDateTime(localDateTime);
                transaction.setCashAmount(cashAmount);
                transaction.setCustomer(customer);
                transaction.setPurchaseId(purchaseId);

                transactionMap.put(transactionId, transaction); // This will now work as the map is mutable

                reverseTransactionMapOrder();

            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void reverseTransactionMapOrder() {
        Map<String, Transaction> reversedMap = new LinkedHashMap<>();
        List<Map.Entry<String, Transaction>> entries = new ArrayList<>(transactionMap.entrySet());

        // Add entries to the reversed map in reverse order
        Collections.reverse(entries);

        for (Map.Entry<String, Transaction> entry : entries) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }

        // Now assign the reversed map back to the transactionMap
        transactionMap = reversedMap;
    }

    public void printTransactionMap() {
        TreeMap<String, Transaction> sortedMap = new TreeMap<>((a, b) -> b.compareTo(a));
        sortedMap.putAll(transactionMap);

        for (Map.Entry<String, Transaction> entry : sortedMap.entrySet()) {
            Transaction transaction = entry.getValue();
            System.out.println("Transaction ID: " + entry.getKey());
            System.out.println("Date & Time: " + transaction.getDateTime());
            System.out.println("Customer: " + transaction.getCustomer());
            System.out.println("Purchase ID: " + transaction.getPurchaseId());
            System.out.println("Amount: " + transaction.getCashAmount());
            System.out.println("---------------------------------------------------");
        }
    }


    public static double calculateGrandTotal(String transactionID, Map<String, Double> productPrices) {
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


    public static Map<String, Integer> getPurchases(String purchaseId) {
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
        return transactionMap.size();
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
