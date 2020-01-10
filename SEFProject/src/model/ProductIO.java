package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ProductIO {
    private ProductList productList;

    public ProductIO (ProductList productList) {
        this.productList = productList;
    }


    // called internally before usage
    public void getData()
    {
        String name;
        double weight;
        double price;
        int stockShelf;
        int stockWarehouse;
        int replenishmentThreshold;
        int reorder;
        String supplierName;
        double revenue;
        // resource reference to tracking_data.txt in res/raw/ folder of your project
        // supports trailing comments with //
        File file = new File("ProductData.txt");
        try
        {
            Scanner scanner = new Scanner(file);
            // match comma and 0 or more whitespace OR trailing space and newline
            scanner.useDelimiter(",\\s*|\\s*\\n+");
            while (scanner.hasNext())
            {


                name = scanner.next();
                weight = Double.parseDouble(scanner.next());
                price = Double.parseDouble(scanner.next());
                stockShelf = Integer.parseInt(scanner.next());
                //currentStockShelf = Integer.parseInt(scanner.next());
                stockWarehouse = Integer.parseInt(scanner.next());
                replenishmentThreshold = Integer.parseInt(scanner.next());
                reorder = Integer.parseInt(scanner.next());
                supplierName = scanner.next();
                revenue = Double.parseDouble(scanner.next());

                productList.addProduct(name,weight, price, stockShelf, stockWarehouse,replenishmentThreshold,reorder,supplierName,revenue);


                /*if (DatabaseHelper.getInstance(context).insertTrackaleData(trackableId, trackableName,trackableDes,trackableURL,trackableCategory)){
                    System.out.println("Succeed");

                }else{
                    System.out.println("Failed");
                }*/
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    public void getDiscountData() {
    	File file = new File("DiscountData.txt");
    	int id;
    	int quantity;
    	double percentage;
        try
        {
            Scanner scanner = new Scanner(file);
            // match comma and 0 or more whitespace OR trailing space and newline
            scanner.useDelimiter(",\\s*|\\s*\\n+");
            while (scanner.hasNext())
            {

                id = Integer.parseInt(scanner.next());
                quantity = Integer.parseInt(scanner.next());
                percentage = Double.parseDouble(scanner.next());
                
                productList.addDiscount(id, quantity, percentage);

                /*if (DatabaseHelper.getInstance(context).insertTrackaleData(trackableId, trackableName,trackableDes,trackableURL,trackableCategory)){
                    System.out.println("Succeed");

                }else{
                    System.out.println("Failed");
                }*/
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
    
    public void saveData(){
       // ArrayList<Product> products = productList.getProductsSize();
        File fold = new File("ProductData.txt");
        fold.delete();
        File fnew = new File("ProductData.txt");
        //System.out.println(source);

        try {
            FileWriter f2 = new FileWriter(fnew, false);
            for (int i = 0; i < productList.getProductsSize().size(); i++){
                String name = productList.getProductsSize().get(i).getName();
                double weight = productList.getProductsSize().get(i).getWeight();
                double price = productList.getProductsSize().get(i).getPrice();
                int stockShelf = productList.getProductsSize().get(i).getShelfStock();
                int stockWarehouse = productList.getProductsSize().get(i).getWarehouseStock();
                int replenishmentLevel = productList.getProductsSize().get(i).getReplenishment();
                int reorder = productList.getProductsSize().get(i).getReorder();
                String supplier = productList.getProductsSize().get(i).getSupplier();
                double revenue = productList.getProductsSize().get(i).getRevenue();

                f2.write(name + ", " + weight + ", " + price + ", " + stockShelf + ", " + stockWarehouse + ", " + replenishmentLevel + ", " + reorder + ", " + supplier + ", " + revenue + "\r\n");

            }
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void saveDiscountData(){
        // ArrayList<Product> products = productList.getProductsSize();
         File fold = new File("DiscountData.txt");
         fold.delete();
         File fnew = new File("DiscountData.txt");
         //System.out.println(source);

         try {
             FileWriter f3 = new FileWriter(fnew, false);
             for (int i = 0; i < productList.getDiscount().size(); i++){

                 int id = productList.getDiscount().get(i).getProductID();
                 int quantity = productList.getDiscount().get(i).getQuantity();
                 double percentage = productList.getDiscount().get(i).getPercentage();

                 f3.write(id + ", " + quantity + ", " + percentage + "\r\n");

             }
             f3.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
