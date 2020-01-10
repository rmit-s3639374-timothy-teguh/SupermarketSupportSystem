package model;

public class WarehouseStaff extends User{
	public WarehouseStaff(String name) {
		super(name);
	}
	public String getCommands() {
		return (super.getCommands() +
				"-> replenish - Replenishes stock level for the given ID \n"
				);
	}
}
