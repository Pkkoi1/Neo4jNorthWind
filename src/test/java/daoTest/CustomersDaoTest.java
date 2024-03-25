package daoTest;

import dao.CustomerDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CustomersDaoTest {

    static CustomerDao customersDao;

    @BeforeAll
    static void setup() {
        customersDao = new CustomerDao();
    }

    @AfterAll
    static void tearDown() {
        customersDao.close();
    }

    @Test
    void testGetNumberCustomerByCity()
    {
        System.out.println(customersDao.getNumberCustomerByCity());
    }
}
