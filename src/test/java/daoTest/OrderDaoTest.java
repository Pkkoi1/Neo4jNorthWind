package daoTest;

import dao.OrderDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDaoTest {

    static OrderDao orderDao;

    @BeforeAll
    static void setup() {
        orderDao = new OrderDao();
    }
    @AfterAll
    static void tearDown() {
        orderDao.close();
    }
    @Test
    void testTotalOrder() {
        System.out.println(orderDao.totalOrder("10248"));
    }
    @Test
    void totalOrderInDay() {
        System.out.println(orderDao.totalAmountOfAnOrderByOrderDate("1996-07-04"));
    }
    @Test
    void totalOrderInMonth() {
        System.out.println(orderDao.totalAmountOfAnOrderByMonth(7, 1996));
    }

}
