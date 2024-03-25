package dao;

import entity.Category;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.List;
import java.util.Map;

public class CategoryDao {

    Driver driver;

    public CategoryDao() {
        driver = utils.AppUtils.driver();
    }

    public boolean addCategory(Category cate)
    {
        String query = "CREATE (c:Category {id: $id, name: $name})";
        Map<String, Object> params = utils.AppUtils.pojoToMap(cate);
        try(Session session = driver.session())
        {
            return session.writeTransaction(tx -> {
                tx.run(query, params).consume();
                return true;
            });
        }
    }
    public boolean deleteCategory(String id)
    {
        String query = "MATCH (c:Category) WHERE c.id = $id DELETE c";
        Map<String, Object> params = Map.of("id", id);
        try(Session session = driver.session())
        {
            return session.writeTransaction(tx -> {
                tx.run(query, params).consume();
                return true;
            });
        }
    }
    public boolean updateCategory(String id, String name)
    {
        String query = "MATCH (c:Category) WHERE c.id = $id SET c.name = $name";
        Map<String, Object> params = Map.of("id", id, "name", name);
        try(Session session = driver.session())
        {
            return session.writeTransaction(tx -> {
                tx.run(query, params).consume();
                return true;
            });
        }
    }
    public List<Category> findList()
    {
        String query = "MATCH (c:Category) RETURN c";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.readTransaction(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("c").asNode())
                        .map(node -> utils.AppUtils.nodeToPOJO(node, Category.class))
                        .toList();
            });
        }
    }
    public void close() {
        driver.close();
    }
}
