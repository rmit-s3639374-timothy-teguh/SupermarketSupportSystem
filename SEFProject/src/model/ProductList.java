
package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;

import controller.WriteFile;

public class ProductList extends Observable{
	private ArrayList<Product> products;
	private ArrayList<Discount> discounts;
	private ArrayList<BuyOrder> buyOrders;
	private int previousQuantity = 0;
	
	public ProductList() {
		products = new ArrayList<Product>();
		discounts = new ArrayList<Discount>();
		buyOrders = new ArrayList<BuyOrder>();
	}
	
	public ArrayList<Discount> getDiscount() {
		return discounts;
	}
	
	public ArrayList<Product> getProductsSize() {
		return products;
	}
	
	public void addProduct(String productName, double weight, double price, int shelfStock, int warehouseStock, int replenishment, int reorder, String supplierName, double revenue) {
		Product product = new Product(productName, weight, price, shelfStock, warehouseStock, replenishment, reorder, supplierName, revenue);
		products.add(product);
	}
	
	public void addDiscount(int ID, int quantity, double percentage) {
		for (int i = 0; i < discounts.size(); i++){
			if (discounts.get(i).getProductID() == ID) {
				setChanged();
				notifyObservers("Discount exists for the specified product ID \n");
				return;
			}
		}
		Discount discount = new Discount(ID, quantity, percentage);
		discounts.add(discount);
	}
	
	public void addBuyOrder(int productID, int quantity) {
		boolean isExist = false;
		int index = 0;
		for (int i = 0; i < buyOrders.size(); i++){
			if (buyOrders.get(i).getProductID() == productID){
				previousQuantity += buyOrders.get(i).getQuantity();
				index = i;
				isExist = true;
			}
		}
		if (quantity + previousQuantity > getProductByID(productID).getShelfStock()){
			previousQuantity = 0;
			setChanged();
			notifyObservers("Quantity exceeds available stock \n");
		}
		else {
			if (isExist){
				buyOrders.get(index).setQuantity(previousQuantity + quantity);
			}
			else{
				BuyOrder buyOrder = new BuyOrder(productID, quantity);
				buyOrders.add(buyOrder);
			}
			getProductByID(productID).setCurrentShelfStock(products.get(productID).getCurrentShelfStock() - quantity);
			previousQuantity = 0;
		}
	}
	
	public void removeBuyOrder(int buyOrderID, int quantity) throws Exception {
		BuyOrder buyOrder = buyOrders.get(buyOrderID);
		Product product = products.get(buyOrder.getProductID());
		if (buyOrder.getQuantity() < quantity) {
			throw new Exception();
		}
		else if(buyOrder.getQuantity() == quantity) {
			buyOrders.remove(buyOrderID);
		}
		else {
			buyOrder.setQuantity(buyOrder.getQuantity() - quantity);
		}
		product.setCurrentShelfStock(product.getCurrentShelfStock() + quantity);
	}
	
	public void removeAllBuyOrders() throws Exception {
		int size = buyOrders.size();
		for (int i = 0; i < size; i++) {
			removeBuyOrder(0, buyOrders.get(0).getQuantity());
		}
	}
	
	public void removeDiscount(int productID) {
		for (int i = 0; i < discounts.size(); i++) {
			if(discounts.get(i).getProductID() == productID) {
				discounts.remove(i);
				break;
			}
		}
	}

	private String productToString(int index, User currentUser) {
		Product product = products.get(index);
		String output = ("Product ID: " + index + "\n" + 
				"Name: " + product.getName() + "\n" +
				"Weight: " + product.getWeight() + "kg \n" +
				"Price: $" + product.getPrice() + "\n" +
				"Stock Remaining: " + product.getCurrentShelfStock() + "\n" +
				"Supplier: " + product.getSupplier() + "\n");
		if (!(currentUser instanceof Customer) && currentUser != null) {
			output = (output + 
					"Warehouse Stock: " + product.getWarehouseStock() + "\n" +
					"Revenue: $" + product.getRevenue() + "\n");
		}
		return output;
	}
	
	private String buyOrderToString(int buyOrderID) {
		BuyOrder buyOrder = buyOrders.get(buyOrderID);
		String output = (productToString(buyOrder.getProductID(), null) +
				"Quantity Ordered: " + buyOrder.getQuantity() + "\n"
				);
		return output;
	}
	
	private String listProducts() {
		String output = "";
		for (int i = 0; i < products.size(); i++) {
			output = output + i + " " + products.get(i).getName() + "\n";
		}
		return output;
	}
	
	private String listDiscounts() {
		String output = "$5 Discount for every 20 Loyalty Points \n";
		for (int i = 0; i < discounts.size(); i++) {
			Discount discount = discounts.get(i);
			output = output + discount.getPercentage() * 100 + "% Discount if buying at least " + discount.getQuantity() + " " + products.get(discount.getProductID()).getName() + "(s) \n";
		}
		return output;
	}

	private String listCart(User currentUser, boolean debug) {
		String output = "#Shopping Cart# \n";
		for (int i = 0; i < buyOrders.size(); i++) {
			int productID = buyOrders.get(i).getProductID();
			output = output + products.get(productID).getName() + " x " + buyOrders.get(i).getQuantity() + " = $" + getSubtotal(buyOrders.get(i));
			if(debug) {
				output = output + " (Buy Order ID = " + i + ")";
			}
			output = output + "\n";
		}
		output = output + "Total = $" + getTotal() + "\n" + "Discounted Total = $" + getDiscountedTotal(currentUser) + "\n";
		return output;
	}
	
	public double getSubtotalDiscount(BuyOrder buyOrder){
		int productID = buyOrder.getProductID();
		int quantity = buyOrder.getQuantity();
		int discountID = -1;
		double subtotalDiscount = 0;
		for (int i = 0; i < discounts.size(); i++) {
			if (discounts.get(i).getProductID() == productID) {
				discountID = i;
				break;
			}
		}
		if (discountID == -1) {
			return 0;
		}
		if (quantity >= discounts.get(discountID).getQuantity()) {
			subtotalDiscount = (products.get(productID).getPrice() * buyOrder.getQuantity()) * discounts.get(discountID).getPercentage();
		}
		return subtotalDiscount;
	}
	
	public double getTotalDiscount() {
		double totalDiscount = 0;
		for (int i = 0; i < buyOrders.size(); i++) {
			totalDiscount += getSubtotalDiscount(buyOrders.get(i));
		}
		return totalDiscount;
	}
	
	private double getSubtotal(BuyOrder buyOrder) {
		int productID = buyOrder.getProductID();
		double subtotal = products.get(productID).getPrice() * buyOrder.getQuantity();
		return subtotal;
	}
	
	public double getTotal() {
		double total = 0;
		for (int i = 0; i < buyOrders.size(); i++) {
			total += getSubtotal(buyOrders.get(i));
		}
		return total;
	}
	
	public double getDiscountedTotal(User currentUser) {
		double discountedTotal = getTotal() - getTotalDiscount();
		Customer customer = (Customer) currentUser;
		int currentLoyalty = customer.getLoyalty();
		for (int i = 20; i <= currentLoyalty;) {
			discountedTotal -= 5;
			currentLoyalty -= 20;

		}
		if (discountedTotal<0) {
			discountedTotal = 0;
		}
		return (Math.round(discountedTotal * 100D) / 100D);	
	}
	
	public Product getProductByID(int productID) {
		return products.get(productID);
	}
	
	public void resetCart() {
		buyOrders.clear();
	}
	
	public void getProduct(int input, User currentUser) {
		setChanged();
        notifyObservers(productToString(input, currentUser));
	}

	public void getAllProducts() {
		setChanged();
        notifyObservers(listProducts());
	}
	
	public void getAllDiscounts() {
		setChanged();
        notifyObservers(listDiscounts());
	}
	
	public void getBuyOrder(int buyOrderID) {
		setChanged();
		notifyObservers(buyOrderToString(buyOrderID));
	}

	public void getBuyOrders(User currentUser) {
		setChanged();
		notifyObservers(listCart(currentUser, false));
	}
	
	public void getBuyOrdersDebug(User currentUser) {
		setChanged();
		notifyObservers(listCart(currentUser, true));
	}

	public void checkOut(User currentUser) {
		Customer customer = (Customer) currentUser;
		double currentBalance = customer.getBalance();
		if (buyOrders.size() == 0) {
			setChanged();
			notifyObservers("Error! There is nothing in the cart. \n");
			return;
		}
		for (int i = 0; i < buyOrders.size(); i++) {
			BuyOrder buyOrder = buyOrders.get(i);
			Product product = products.get(buyOrder.getProductID());
			product.setShelfStock(product.getShelfStock() -  buyOrder.getQuantity());
			product.setCurrentShelfStock(product.getShelfStock());
			product.addToRevenue(getSubtotal(buyOrder));
		}
		double discountedTotal = getDiscountedTotal(currentUser);
		if(currentBalance < discountedTotal) {
			setChanged();
			notifyObservers("Error! You have insufficient funds. \n");
			return;
		}
		customer.setBalance(currentBalance - discountedTotal);
		int currentLoyalty = customer.getLoyalty();
		for (int i = 20; i <= currentLoyalty;) {
			customer.setLoyalty(currentLoyalty -= 20);
		}
		for (int i = 10; i <= discountedTotal;) {
			discountedTotal -= 10;
			customer.setLoyalty(currentLoyalty +=1);
		}
		String fileName = "Report.txt";
			
		try {
			WriteFile data = new WriteFile(fileName, true);
			
			
			
			Date today = Calendar.getInstance().getTime();
			
			String customerName  = currentUser.getName();
			int productID;
			String productName;
			double productPrice;
			int quantityBought;
			double subtotal;
			double total = 0;
			
			String text = "Report " + "\r\n" + "\r\n" + "Date :" + today + "\r\n" + "\r\n" + "Customer			: " + customerName;
			
			
			for (int i = 0; i < buyOrders.size(); i++) {
	
				productID = buyOrders.get(i).getProductID();
				productName = products.get(buyOrders.get(i).getProductID()).getName();
				productPrice = products.get(buyOrders.get(i).getProductID()).getPrice();
				quantityBought = buyOrders.get(i).getQuantity();
				subtotal = productPrice * quantityBought;
				total += subtotal;
				
				
				
				text += "\r\n" + "\r\n" + "Product				: " + productName + " (Product ID : " + productID + ")" +
						"\r\n" + "Price				: " + productPrice + 
						"\r\n" + "Quantity Bought		: " + quantityBought +
						"\r\n" + "Subtotal			: " + subtotal;
			}
		
			text += "\r\n" + "\r\n" +  "Cart Total			: " + total + "\r\n" + "_______________________________________________" + "\r\n" + "\r\n";
		
			data.writeToFile(text);
		} 
		catch (Exception e) {
			
		}	
		setChanged();
		notifyObservers("Thank you for shopping. \n");
		resetCart();
	}

	public void replenish(int productID, int quantity) {
		if (quantity > getProductByID(productID).getWarehouseStock()){
			setChanged();
			notifyObservers("Not enough warehouse stock. \n");
			return;
		}
		Product product = products.get(productID);
		product.setWarehouseStock(product.getWarehouseStock() - quantity);
		product.setShelfStock(product.getShelfStock() + quantity);
		product.setCurrentShelfStock(product.getShelfStock());
		setChanged();
		notifyObservers("Shelf stock of " + product.getName() + " replenished by " + quantity + "\n");
		if (getProductByID(productID).getWarehouseStock() < getProductByID(productID).getReplenishment()){
			reorder(productID);
		}
	}
	
	public void revenueReport() {
		String fileName1 = "Revenue.txt";
		
		try {
			String revenue = "Revenue	:";

			WriteFile data1 = new WriteFile(fileName1, false);
			
			for (int i = 0; i < products.size(); i++) {
				revenue += "\r\n" + products.get(i).getName() + " " + products.get(i).getRevenue();
				}
			
			data1.writeToFile(revenue);
		}
		
		catch (Exception e) {
			
		}
		setChanged();
		notifyObservers("Revenue report generated as Revenue.txt \n");
	}
	
	public void reorder(int productID) {
		Product product = getProductByID(productID);
		product.setWarehouseStock(product.getWarehouseStock() + product.getReorder());
		setChanged();
		notifyObservers("Warehouse stock of " + product.getName() + " reordered by " + product.getReorder() + "\n");
	}
}
