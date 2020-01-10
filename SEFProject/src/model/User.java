package model;

public abstract class User {
	private String name;
	
	public User (String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String getCommands() {
		return ("Welcome " + getName() + "! Here is a list of commands that you can use: \n" +
				"-> search - Searches for a product with the given ID \n" +
				"-> list - Lists all available products for purchase \n" +
				"-> discounts - Lists all available discounts \n"
				);
	}
}
