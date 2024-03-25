package daoTest;

import dao.ProductDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProductDaoTest {

    static ProductDao productDao;
    @BeforeAll
    static void setup() {
        productDao = new ProductDao();
    }
    @AfterAll
    static void tearDown() {
        productDao.close();
    }
    @Test
    void testFindList()
    {
        productDao.findProductBySupplier("Exotic Liquids").forEach(System.out::println);
    }

    @Test
    void testMaxPrice()
    {
        productDao.findMaxPrice().forEach(System.out::println);
    }
    @Test
    void testtotalOrders()
    {
        System.out.println(productDao.totalOrderOfCustomers());
    }
    @Test
    void testtotalProductSold()
    {
        System.out.println(productDao.totalProductSold());
    }
    @Test
    void searchProductByName()
    {
        System.out.println(productDao.searchProductByName("Chai"));
    }
}
