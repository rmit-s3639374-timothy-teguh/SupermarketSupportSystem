package model;

public class Manager extends User{

	public Manager(String name) {
		super(name);
	}
	
	public String getCommands() {
		return (super.getCommands() +
				"-> add_discount - Adds discount details for the specified product \n" +
				"-> remove_discount - Removes discount from the specified product \n" +
				"-> revenue - Get details about the revenue of each product \n"
				);
	}
}
