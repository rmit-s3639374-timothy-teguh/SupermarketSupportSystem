package TestCase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.BuyOrder;
import model.Customer;
import model.Product;
import model.ProductList;
import model.User;

class TestDiscounts {

		
		User user;
		ProductList productlist;
		Product product;
		BuyOrder buyOrder;

		@BeforeEach
		void setUp() {
			//add customer
			user = new Customer("Long", 100, 40);
			productlist = new ProductList();
			productlist.addProduct("Apple", 0.1, 1, 20, 100, 50, 100, "Fresh Fruits.co", 0);
			productlist.addProduct("Banana", 0.11, 1.5, 10, 100, 50, 100, "Fresh Fruits.co", 0);
			productlist.addDiscount(0,1,0.1);
			productlist.addDiscount(1,2,0.2);
			product = productlist.getProductByID(0);
		}

		@Test
		void testSubtotalDiscount() {
			buyOrder = new BuyOrder(0, 1);
			assertEquals(0.1,productlist.getSubtotalDiscount(buyOrder));
		}
		
		@Test
		void testTotal() {
			productlist.addBuyOrder(0, 20);
			assertEquals(8,productlist.getDiscountedTotal(user));
		}
		
		
		
	}
