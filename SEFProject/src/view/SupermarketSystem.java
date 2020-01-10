package view;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controller.ProductController;
import controller.UserController;
import model.Customer;
import model.Manager;
import model.User;
import model.WarehouseStaff;

public class SupermarketSystem implements Observer {
	private ProductController productController;
	private UserController userController;
	
	private User currentUser;
	
	private Scanner scanner;
	
	public SupermarketSystem() {
		productController = new ProductController(this);
		userController = new UserController(this);
		scanner = new Scanner(System.in);
		currentUser = null;
	}
	
	public void run() {
		productController.load();
		userController.load();
		login();
		while(true) {
			listCommands();
			System.out.println();
			System.out.print("Enter Command: ");
			String input = scanner.nextLine();
			switch(input) {
				case "search":
					while(true) {
						System.out.print("Enter Product ID or 'X' to cancel: ");
						try {
							String productID = scanner.nextLine();
							System.out.println();
							if(productID.equals("X") || productID.equals("x")) {
								break;
							}
							productController.displayProduct(Integer.parseInt(productID), currentUser);
						}
						catch(Exception e) {
							printInvalidInput();
							continue;
						}
						break;
					}
					break;
				case "list":
					System.out.println();
					productController.displayAllProducts();
					break;
				case "discounts":
					System.out.println();
					productController.displayAllDiscounts();
					break;
				case "buy":
					if (!(currentUser instanceof Customer)) {
						System.out.println();
						printInvalidInput();
						break;
					}
					while(true) {
						System.out.print("Enter Product ID or 'X' to cancel: ");
						try {							
							String productID1 = scanner.nextLine();
							System.out.println();
							if(productID1.equals("X") || productID1.equals("x")) {
								break;
							}
							productController.displayProduct(Integer.parseInt(productID1), currentUser);
							System.out.print("Enter Quantity or 'X' to cancel: ");
							String quantityString = scanner.nextLine();
							System.out.println();
							if(quantityString.equals("X") || quantityString.equals("x")) {
								break;
							}
							int quantity1 = Integer.parseInt(quantityString);
							if(quantity1>0) {
								productController.addToCart((Integer.parseInt(productID1)), quantity1);
								productController.displayCart(currentUser);
							}
							else
							{
								printInvalidInput();
								continue;
							}
						}
						catch(Exception e) {
							printInvalidInput();
							continue;
						}
						break;
					}
					break;
				case "card":
					if (!(currentUser instanceof Customer)) {
						System.out.println();
						printInvalidInput();
						break;
					}
					System.out.println();
					userController.displayCardInfo();
					break;
				case "cart":
					if (!(currentUser instanceof Customer)) {
						System.out.println();
						printInvalidInput();
						break;
					}
					System.out.println();
					productController.displayCart(currentUser);
					break;
				case "help":
					if (!(currentUser instanceof Customer)){
						System.out.println();
						printInvalidInput();
						break;
					}
					System.out.println();
					help();
					break;
				case "checkout":
					if (!(currentUser instanceof Customer)) {
						System.out.println();
						printInvalidInput();
						break;
					}
					System.out.println();
					productController.checkOut(currentUser);
					productController.save();
					userController.save();
					break;
				case "replenish":
					if (!(currentUser instanceof WarehouseStaff)) {
						System.out.println();
						printInvalidInput();
						break;
					}
					while(true){
						System.out.print("Enter Product ID or 'X' to cancel: ");
						try {
							String productID2 = scanner.nextLine();
							System.out.println();
							if(productID2.equals("X") || productID2.equals("x")) {
								break;
							}
							productController.displayProduct(Integer.parseInt(productID2), currentUser);
							System.out.print("Enter Quantity or 'X' to cancel: ");
							String quantityString1 = scanner.nextLine();
							System.out.println();
							if(quantityString1.equals("X") || quantityString1.equals("x")) {
								break;
							}
							int quantity2 = Integer.parseInt(quantityString1);
							if(quantity2>0) {
								productController.replenish(Integer.parseInt(productID2), quantity2);}
							else{
								printInvalidInput();
								continue;
							}
						}
						catch(Exception e) {
							printInvalidInput();
							continue;
						}
						break;	
					}
					break;
				case "add_discount":
					if (!(currentUser instanceof Manager)) {
						System.out.println();
						printInvalidInput();
						break;
					}
					while(true) {
						System.out.print("Enter Product ID or 'X' to cancel: ");
						try {							
							String productID1 = scanner.nextLine();
							System.out.println();
							if(productID1.equals("X") || productID1.equals("x")) {
								break;
							}
							productController.displayProduct(Integer.parseInt(productID1), currentUser);
							System.out.print("Enter Quantity or 'X' to cancel: ");
							String quantityString = scanner.nextLine();
							System.out.println();
							if(quantityString.equals("X") || quantityString.equals("x")) {
								break;
							}
							System.out.print("Enter Percentage (Decimal) or 'X' to cancel: ");
							String percentage = scanner.nextLine();
							System.out.println();
							if(percentage.equals("X") || percentage.equals("x")) {
								break;
							}
							int productID2 = Integer.parseInt(productID1);
							int quantity1 = Integer.parseInt(quantityString);
							double percentage1 = Double.parseDouble(percentage);
							if(quantity1>0) 
								productController.addDiscount(productID2, quantity1, percentage1);
							else
							{
								printInvalidInput();
								continue;
							}
						}
						catch(Exception e) {
							printInvalidInput();
							continue;
						}
						break;
					}
					break;
				case "remove_discount":
					if (!(currentUser instanceof Manager)) {
						System.out.println();
						printInvalidInput();
						break;
					}
					while(true) {
						System.out.print("Enter Product ID or 'X' to cancel: ");
						try {
							String productID = scanner.nextLine();
							System.out.println();
							if(productID.equals("X") || productID.equals("x")) {
								break;
							}
							productController.removeDiscount(Integer.parseInt(productID));
						}
						catch(Exception e) {
							printInvalidInput();
							continue;
						}
						break;
					}
					break;
				case "revenue":
					if (!(currentUser instanceof Manager)) {
						System.out.println();
						printInvalidInput();
						break;
					}
					productController.revenueReport();
					break;
				case "quit":
					productController.save();
					userController.save();
					System.out.println();
					System.out.println("Please come again!");
					return;
				default:
					System.out.println();
					printInvalidInput();
			}
		}
	}

	public void login() {
		while(true) {
			System.out.print("Enter your ID: ");
			try {
				String input = scanner.nextLine();
				System.out.println();
				userController.login(Integer.parseInt(input));
			}
			catch(Exception e) {
				printInvalidInput();
				continue;
			}
			System.out.println("Login successful.");
			System.out.println();
			break;
		}
	}
	
	private void help() {
		while(true) {
			System.out.print("Enter Sales Staff ID or 'X' to cancel: ");
			try {
				String input = scanner.nextLine();
				System.out.println();
				if(input.equals("X") || input.equals("x")) {
					System.out.println();
					break;
				}
				userController.saleStaffLogin(Integer.parseInt(input));
			}
			catch(Exception e) {
				printInvalidInput();
				continue;
			}
			System.out.println("Login successful.");
			System.out.println();
			break;
		}
		while(true) {
			System.out.println("This is the current customer's cart: \n");
			productController.displayCartDebug(currentUser);
			System.out.println("Here is a list of commands that you can use to edit the cart:");
			System.out.println("-> delete - Removes a specific buy order from the cart");
			System.out.println("-> delete_all - Removes all buy orders");
			System.out.println("-> done - Ends this help session \n");
			System.out.print("Enter Command: ");
			String input = scanner.nextLine();
			switch(input) {
				case "delete":
					while(true) {
						System.out.print("Enter Buy Order ID or 'X' to cancel: ");
						try {							
							String buyOrderID = scanner.nextLine();
							System.out.println();
							if(buyOrderID.equals("X") || buyOrderID.equals("x")) {
								break;
							}
							productController.displayBuyOrder(Integer.parseInt(buyOrderID));
							System.out.print("Enter Quantity or 'X' to cancel: ");
							String quantityString = scanner.nextLine();
							System.out.println();
							if(quantityString.equals("X") || quantityString.equals("x")) {
								break;
							}
							int quantity = Integer.parseInt(quantityString);
							productController.removeBuyOrder((Integer.parseInt(buyOrderID)), quantity);
						}
						catch(Exception e) {
							printInvalidInput();
							continue;
						}
						break;
					}
					break;
				case "delete_all":
					System.out.println();
					try {
						productController.removeAllBuyOrders();
					} catch (Exception e) {
						break;
					}
					break;
				case "done":
					System.out.println();
					return;
				default:
					System.out.println();
					printInvalidInput();
			}
		}
	}
	
	public void listCommands() {
		System.out.println(currentUser.getCommands() + "-> quit - Quits program");
	}
	
	public void printInvalidInput() {
		System.out.println("Invalid input.");
		System.out.println();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof User) {
			currentUser = (User) arg;
		}
		else if (arg instanceof String) {
			System.out.println(arg);
		}
	}
}
