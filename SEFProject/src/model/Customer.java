package model;

public class Customer extends User{

	private double balance;
	private int loyalty;
	
	public Customer(String name, double balance, int loyalty) {
		super(name);
		this.balance = balance;
		this.loyalty = loyalty;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double value) {
		balance = value;
	}
	
	public int getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(int value) {
		loyalty = value;
	}
	
	public String getCommands() {
		return (super.getCommands() +
				"-> buy - Adds a product with the given ID to the cart \n" +
				"-> card - Displays card information \n" +
				"-> cart - Lists all current buy orders \n" +
				"-> help - Notifies a staff member who will help you change your buy orders \n" +
				"-> checkout - Checks out everything in the cart \n"
				);
	}
}
