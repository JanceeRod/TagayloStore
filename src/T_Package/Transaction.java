package T_Package;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Transaction {
	private String transactionId;
	private LocalDateTime dateTime;
	private String purchaseId;
	private double cashAmount;
	private String customer;

	public Transaction() {
		this.transactionId = generateTransactionId();
		this.dateTime = LocalDateTime.now();
		this.purchaseId = generatePurchaseId();
		this.cashAmount = 0;
		this.customer = "";
	}

	// Getters
	public String getTransactionId() {
		return transactionId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public double getCashAmount() {
		return cashAmount;
	}

	public String getCustomer() {
		return customer;
	}

	// Setters
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public void setCustomer(String customer) {
		this.customer = customer.isEmpty() ? "Walk-in Customer" : customer;
	}

	private String generateTransactionId() {
		return "TXN-" + System.currentTimeMillis();
	}

	private String generatePurchaseId() {
		return "PUR-" + System.currentTimeMillis();
	}

	public void addCustomerOrders(HashMap<String, Integer> customerOrders) {
		String purchaseFilePath = "transactionPurchases.csv";
		savePurchaseItems(customerOrders, purchaseFilePath);
	}

	private void savePurchaseItems(HashMap<String, Integer> customerOrders, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			boolean fileExists = new java.io.File(filePath).exists();
			if (!fileExists) {
				writer.write("PurchaseId,ProductCode,Quantity");
				writer.newLine();
			}

			for (Map.Entry<String, Integer> entry : customerOrders.entrySet()) {
				String productCode = entry.getKey();
				int quantity = entry.getValue();
				writer.write(purchaseId + "," + productCode + "," + quantity);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing purchase items to file: " + e.getMessage());
		}
	}

	public String toCSVString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return transactionId + "," +
				dateTime.format(formatter) + "," +
				purchaseId + "," +
				cashAmount + "," +
				customer;
	}

	public void saveToCSV(String filePath) {
		boolean fileExists = new java.io.File(filePath).exists();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			if (!fileExists) {
				writer.write("TransactionId,DateTime,PurchaseId,CashAmount,Customer");
				writer.newLine();
			}

			writer.write(toCSVString());
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}
}