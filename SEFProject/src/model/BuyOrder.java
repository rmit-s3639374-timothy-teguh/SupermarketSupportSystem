package model;

public class BuyOrder {
	
	private int productID;
	private int quantity;
	
	public BuyOrder(int productID, int quantity) {
		this.productID = productID;
		this.quantity = quantity;
	}
	
	public int getProductID() {
		return productID;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
