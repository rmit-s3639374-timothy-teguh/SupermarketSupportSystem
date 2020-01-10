package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserIO {
    private UserList userList;

    public UserIO (UserList userList) {
        this.userList = userList;
    }

    public void getUserData()
    {
        String user;
        String name;

        // resource reference to tracking_data.txt in res/raw/ folder of your project
        // supports trailing comments with //
        File file = new File("UserData.txt");
        try
        {
            Scanner userScanner = new Scanner(file);
            // match comma and 0 or more whitespace OR trailing space and newline
            userScanner.useDelimiter(",\\s*|\\s*\\n+");
            while (userScanner.hasNext())
            {
                user = userScanner.next();
                if (user.equals("Customer")) {
                    name = userScanner.next();

                    double balance = Double.parseDouble(userScanner.next());
                    int loyalty = Integer.parseInt(userScanner.next());

                    userList.addCustomer(name, balance, loyalty);
                }else if (user.equals("SalesStaff")){
                    name = userScanner.next();
                    userList.addSalesStaff(name);
                }else if (user.equals("WarehouseStaff")){
                    name = userScanner.next();
                    userList.addWarehouseStaff(name);
                }else if (user.equals("Manager")){
                    name = userScanner.next();
                    userList.addManager(name);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }


   public void saveUserData(){
        // ArrayList<Product> products = productList.getProductsSize();
        File userFileOld =new File("UserData.txt");
        userFileOld.delete();
        File userFileNew=new File("UserData.txt");
        //System.out.println(source);

        try {
            FileWriter f1 = new FileWriter(userFileNew, false);
            for (int i = 0; i < userList.getUsers().size(); i++){

                if (userList.getUsers().get(i) instanceof Customer){
                    String name = userList.getUsers().get(i).getName();
                    double balance = ((Customer) userList.getUsers().get(i)).getBalance();
                    int loyalty = ((Customer) userList.getUsers().get(i)).getLoyalty();
                    f1.write("Customer" + ", " + name + ", " + balance + ", " + loyalty + "\r\n");
                } else if (userList.getUsers().get(i) instanceof SalesStaff){
                    String name = userList.getUsers().get(i).getName();
                    f1.write("SalesStaff" + ", " + name + "\r\n");
                } else if (userList.getUsers().get(i) instanceof WarehouseStaff){
                    String name = userList.getUsers().get(i).getName();
                    f1.write("WarehouseStaff" + ", " + name + "\r\n");
                } else if (userList.getUsers().get(i) instanceof Manager){
                    String name = userList.getUsers().get(i).getName();
                    f1.write("Manager" + ", " + name + "\r\n");
                }


            }
            f1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
