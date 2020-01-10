package controller;

import model.ProductIO;
import model.ProductList;
import model.User;
import view.SupermarketSystem;

public class ProductController {
	
	private ProductList productList;
	private ProductIO productIO;
	
	public ProductController(SupermarketSystem supermarketSystem) {
		productList = new ProductList();
		productList.addObserver(supermarketSystem);
		productIO = new ProductIO(productList);
	}
	
	public void load() {
		productIO.getData();
		productIO.getDiscountData();
		//productList.addDiscount(0,3,0.1);
		//productList.addDiscount(1,2,0.2);
	}
	
	public void save() {
		productIO.saveData();
		productIO.saveDiscountData();
	}

	public void displayProduct(int input, User currentUser) {
		productList.getProduct(input, currentUser);
	}
	
	public void displayAllProducts() {
		productList.getAllProducts();
	}
	
	public void displayAllDiscounts() {
		productList.getAllDiscounts();
	}

	public void displayCart(User currentUser) {
		productList.getBuyOrders(currentUser);
	}
	
	public void displayCartDebug(User currentUser) {
		productList.getBuyOrdersDebug(currentUser);
	}

	public void addToCart(int productID, int quantity) {
		productList.addBuyOrder(productID, quantity);
	}

	public void checkOut(User currentUser) {
		productList.checkOut(currentUser);
	}

	public void replenish(int productID, int quantity) {
		productList.replenish(productID, quantity);
	}

	public void displayBuyOrder(int buyOrderID) {
		productList.getBuyOrder(buyOrderID);
	}
	
	public void addDiscount(int productID, int quantity, double percentage) {
		productList.addDiscount(productID, quantity, percentage);
	}
	
	public void removeDiscount(int productID) {
		productList.removeDiscount(productID);
	}

	public void removeBuyOrder(int buyOrderID, int quantity) throws Exception {
		productList.removeBuyOrder(buyOrderID, quantity);
	}

	public void removeAllBuyOrders() throws Exception {
		productList.removeAllBuyOrders();
	}

	public void revenueReport() {
		productList.revenueReport();
	}
}
