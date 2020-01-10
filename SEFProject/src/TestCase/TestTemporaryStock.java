package TestCase;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Customer;
import model.Product;
import model.ProductList;
import model.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class TestTemporaryStock {

    User user;
    ProductList productlist;
    Product product;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        productlist = new ProductList();
        productlist.addProduct("Durian", 0.1, 1, 20, 100, 50, 100, "Fresh Fruits.co", 0);
        product = productlist.getProductByID(0);
        productlist.addBuyOrder(0, 10 );

    }

    @Test
    void testCurrentTemporaryRemainingStock() {
        //checkout
        assertEquals(10, product.getCurrentShelfStock());
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

}