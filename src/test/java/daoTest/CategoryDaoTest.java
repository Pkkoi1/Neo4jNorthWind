package daoTest;

import dao.CategoryDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CategoryDaoTest {
    static CategoryDao categoryDao;

    @BeforeAll
    static void setup() {
        categoryDao = new CategoryDao();
    }
    @AfterAll
    static void tearDown() {
        categoryDao.close();
    }

    @Test
    void testFindList()
    {
        categoryDao.findList().forEach(System.out::println);
    }
}
