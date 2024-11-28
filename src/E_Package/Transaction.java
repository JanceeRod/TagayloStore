package E_Package;

import B_Package.userOperations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transaction {
	private String transactionId;
	private LocalDateTime dateTime;
	private List<String[]> productsPurchased;
	private double totalAmount;
	private double cashAmount;
	private double change;
	private String customer;

	public Transaction() {
		this.transactionId = generateTransactionId();
		this.dateTime = LocalDateTime.now();
		this.productsPurchased = new ArrayList<>();
		this.totalAmount = 0;
		this.cashAmount = 0;
		this.change = 0;
		this.customer = "";
	}

	// Getters
	public String getTransactionId() {
		return transactionId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public List<String[]> getProductsPurchased() {
		return productsPurchased;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public double getCashAmount() {
		return cashAmount;
	}

	public double getChange() {
		return change;
	}

	// Setters
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
		calculateChange();
	}

	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
		calculateChange();
	}

	public void setCustomer(String customer) {
		this.customer = customer.isEmpty() ? "Walk-in Customer" : customer;
	}

	// Methods
	public void addCustomerOrders(HashMap<String, Integer> customerOrders) {
		for (Map.Entry<String, Integer> entry : customerOrders.entrySet()) {
			String productCode = entry.getKey();
			int quantity = entry.getValue();
			double price = Double.parseDouble(userOperations.getProductPrice(productCode));

			addProduct(productCode, quantity, price);
		}
	}

	public void clearCustomerOrders(HashMap<String, Integer> customerOrders) {
		customerOrders.clear();
		productsPurchased.clear();
		recalculateTotalAmount();
	}

	public void addProduct(String productCode, int quantity, double price) {
		// Add productCode and quantity to the list
		productsPurchased.add(new String[]{productCode, String.valueOf(quantity)});

		// Update totalAmount
		double productTotal = price * quantity;
		totalAmount += productTotal;
		calculateChange(); // Recalculate change after adding a product
	}

	public void removeProduct(String productCode) {
		productsPurchased.removeIf(product -> product[0].equals(productCode));
		recalculateTotalAmount();
	}

	private void recalculateTotalAmount() {
		totalAmount = 0;
		for (String[] product : productsPurchased) {
			int quantity = Integer.parseInt(product[1]);
			double price = Double.parseDouble(userOperations.getProductPrice(product[0]));
			totalAmount += price * quantity;
		}
		calculateChange(); // Recalculate change after adjusting totalAmount
	}

	private void calculateChange() {
		change = cashAmount - totalAmount;
		System.out.println("Change calculated: " + change);
	}

	private String generateTransactionId() {
		return "TXN-" + System.currentTimeMillis();
	}

	public String toCSVString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return transactionId + "," +
				dateTime.format(formatter) + "," +
				Arrays.deepToString(productsPurchased.toArray()) + "," +
				totalAmount + "," +
				cashAmount + "," +
				change + "," +
				customer;
	}

	public void saveToCSV(String filePath) {
		boolean fileExists = new java.io.File(filePath).exists();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			if (!fileExists) {
				writer.write("TransactionId,DateTime,ProductsPurchased,TotalAmount,CashAmount,Change,Customer");
				writer.newLine();
			}

			writer.write(toCSVString());
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}
}