package model;

import java.util.ArrayList;
import java.util.Observable;

public class UserList extends Observable{
	private ArrayList<User> users;
	private User currentUser;
	
	public UserList() {
		users = new ArrayList<User>();
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public void addCustomer(String userName, double balance, int loyalty) {
		User user = new Customer(userName, balance, loyalty);
		users.add(user);
	}
	
	public void addSalesStaff(String userName) {
		User user = new SalesStaff(userName);
		users.add(user);
	}
	
	public void addWarehouseStaff(String userName) {
		User user = new WarehouseStaff(userName);
		users.add(user);
	}
	
	public void addManager(String userName) {
		User user = new Manager(userName);
		users.add(user);
	}

	public void login(int index) {
		currentUser = users.get(index);
		setChanged();
        notifyObservers(currentUser);
	}

	public void getCardInfo() {
		Customer customer = (Customer) currentUser;
		String output = ("Name: " + customer.getName() + "\n" +
						"Balance: $" + customer.getBalance() + "\n" +
						"Loyalty Points: " + customer.getLoyalty() + "\n"
						);
		setChanged();
		notifyObservers(output);
	}

	public void saleStaffLogin(int index) throws Exception {
		// TODO Auto-generated method stub
		if(!(users.get(index) instanceof SalesStaff)) {
			throw new Exception();
		}
	}

}
