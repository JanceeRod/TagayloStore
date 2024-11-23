package E_Package;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//import mainPackage.Transaction.ProductInfo;

public class Transaction {
	private String transactionId;
	private LocalDateTime dateTime;
	private String productPurchased;
	private double price;
	private int quantity;
	private double subtotal;
	private double totalAmount;
	private double cashAmount;
	private double change;
	private String customer;
	
	public Transaction() {
		this.transactionId = getTransactionId();
		this.dateTime = getDateTime();
		this.productPurchased = getProductPurchased();
		this.price = getPrice();
		this.quantity = getQuantity();
		this.subtotal = getSubtotal();
		this.totalAmount = getTotalAmount();
		this.cashAmount = getCashAmount();
		this.change = getChange();
		this.customer = getCustomer();
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public String getProductPurchased() {
		return productPurchased;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getSubtotal() {
		return subtotal;
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
	
	public String getCustomer() {
		return customer;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public void setProductPurchased(String productPurchased) {
		this.productPurchased = productPurchased;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setSubTotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}
	
	public void setChange(double change) {
		this.change = change;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public String toCsvString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd    HH:mm:ss a");
		return transactionId + "," +
        dateTime.format(formatter) + "," +
        productPurchased + "," +
        price + "," +
        quantity + "," +
        totalAmount + "," +
        cashAmount + "," +
        change + "," +
        customer;
	}
	
	private List<ProductInfo> productInfoList = new ArrayList<>();
	
	public void addProductInfo(String productName, double price, int quantity, double totalAmount) {
		ProductInfo productInfo = new ProductInfo(productName, price, quantity, totalAmount);
		productInfoList.add(productInfo);
	}
	
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}
	
	public static class ProductInfo {
		private String productName;
		private double price;
		private int quantity;
		private double totalAmount;
		
		public ProductInfo(String productName, double price, int quantity, double totalAmount) {
			this.productName = productName;
			this.price = price;
			this.quantity = quantity;
			this.totalAmount = totalAmount;
		}
		
		public String getProductName() {
			return productName;
		}
		
		public double getPrice() {
			return price;
		}
		
		public int getQuantity() {
			return quantity;
		}
		
		public double getTotalAmount() {
			return totalAmount;
		}
	}
}
