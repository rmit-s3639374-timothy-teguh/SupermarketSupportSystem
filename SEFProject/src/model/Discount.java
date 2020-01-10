package model;

public class Discount {
	private int productID;
	private int quantity;
	private double percentage;
	
	public Discount(int productID, int quantity, double percentage) {
		this.productID = productID;
		this.quantity = quantity;
		this.percentage = percentage;
	}
	
	public int getProductID() {
		return productID;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getPercentage() {
		return percentage;
	}
	
	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	
	
	
}

