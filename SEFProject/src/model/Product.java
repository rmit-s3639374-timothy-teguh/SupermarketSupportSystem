package model;

public class Product {
	private String name;
	private double weight;
	private double price;
	private int stockShelf;
	private int currentStockShelf;
	private int stockWarehouse;
	private int replenishment;
	private int reorder;
	private String supplierName;
	private double revenue;
	
	public Product(String name, double weight, double price, int stockShelf, int stockWarehouse, int replenishment, int reorder, String supplierName, double revenue) {
		this.name = name;
		this.weight = weight;
		this.price = price;
		this.stockShelf = stockShelf;
		this.currentStockShelf = stockShelf;
		this.stockWarehouse = stockWarehouse;
		this.replenishment = replenishment;
		this.reorder = reorder;
		this.supplierName = supplierName;
		this.revenue = revenue;
	}
	
	public String getName() {
		return name;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getShelfStock() {
		return stockShelf;
	}
	
	public void setShelfStock(int quantity) {
		stockShelf = quantity;
	}
	
	public int getCurrentShelfStock() {
		return currentStockShelf;
	}
	
	public void setCurrentShelfStock(int currentStockShelf) {
		this.currentStockShelf = currentStockShelf;
	}
	
	public int getWarehouseStock() {
		return stockWarehouse;
	}
	
	public void setWarehouseStock(int quantity) {
		stockWarehouse = quantity;
	}
	
	public int getReplenishment() {
		return replenishment;
	}
	
	public int getReorder() {
		return reorder;
	}
	
	public String getSupplier() {
		return supplierName;
	}
	
	public double getRevenue() {
		return revenue;
	}
	
	public void addToRevenue(double value) {
		revenue += value;
	}
}
