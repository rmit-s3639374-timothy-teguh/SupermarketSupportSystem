package controller;

import model.UserIO;
import model.UserList;
import view.SupermarketSystem;

public class UserController {
	
	private UserList userList;
	private UserIO userIO;
	
	public UserController(SupermarketSystem supermarketSystem) {
		userList = new UserList();
		userList.addObserver(supermarketSystem);
		userIO = new UserIO(userList);
	}
	
	public void load() {
		userIO.getUserData();
		/*userIO.getSalesStaffData();
		userIO.getWarehouseStaffData();
		userIO.getManagerData();*/
		//userList.addSalesStaff("Kim");
		//userList.addWarehouseStaff("Frank");
		//userList.addManager("Tim");
	}
	
	public void save() {
		userIO.saveUserData();
	}

	public void login(int input) {
		userList.login(input);
	}

	public void displayCardInfo() {
		userList.getCardInfo();
	}

	public void saleStaffLogin(int input) throws Exception {
		userList.saleStaffLogin(input);
	}


}
