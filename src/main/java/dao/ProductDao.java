package dao;

import entity.Customer;
import entity.Product;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import utils.AppUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDao {
    Driver driver;

    public ProductDao() {
        driver = utils.AppUtils.driver();
    }

    public void close() {
        driver.close();
    }

    public List<Product> findProductBySupplier(String supplier) {
        String query = "MATCH (s:Supplier) - [:SUPPLIES] -> (p:Product) WHERE s.companyName = $supplier RETURN p";
        Map<String, Object> params = Map.of("supplier", supplier);
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("p").asNode())
                        .map(node -> AppUtils.nodeToPOJO(node, Product.class))
                        .toList();
            });
        }
    }
//    Danh sách các sản phẩm có giá bán cao nhất
    public List<Product> findMaxPrice()
    {
        String query = "MATCH (p:Product) WITH max(p.unitPrice) as maxPrice MATCH (p:Product) WHERE p.unitPrice = maxPrice RETURN p";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("p").asNode())
                        .map(node -> AppUtils.nodeToPOJO(node, Product.class))
                        .toList();
            });
        }
    }
    public Map<Customer, Integer> totalOrderOfCustomers()
    {
        String query = "MATCH (c:Customer) - [:PURCHASED] -> (o:Order) RETURN c, count(o) as total ORDER BY total DESC";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> AppUtils.nodeToPOJO(record.get("c").asNode(), Customer.class),
                                record -> record.get("total").asInt(),
                                (a, b) -> a,
                                LinkedHashMap::new
                        ));
            });
        }
    }
    public Map<String, Integer> totalProductSold()
    {
        String query = "MATCH (o:Order) - [r:ORDERS] -> (p:Product) RETURN p.productName as name, sum(r.quantity) as total ORDER BY total DESC";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("name").asString(),
                                record -> record.get("total").asInt(),
                                (a, b) -> a,
                                LinkedHashMap::new
                        ));
            });
        }
    }
    //	Create text index for Product.productName
//  CREATE FULLTEXT INDEX text_index_pName
//		for (p:Product) on EACH [p.productName]
    /**
     * Search products by name
     *
     * @param name
     * @return List of products
     */
    public List<Product> searchProductByName(String name) {

        String query = "CALL db.index.fulltext.queryNodes('text_index_pName', $name) YIELD node RETURN node";
        Map<String, Object> pars = Map.of("name", name);

        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, pars);
                return result.stream().map(Record -> Record.get("node").asNode())
                        .map(node -> AppUtils.nodeToPOJO(node, Product.class)).toList();
            });
        }
    }
//    LOAD CSV WITH HEADERS FROM "file:///music/Album.csv" AS row
//    MERGE (album:Album { id: row.id})
//    SET album.title = row.title, album.price = toFloat(row.price), album.yearOfRelease = toInteger(row.yearOfRelease)
//return album
//    Load file artist.csv
//    id, name, birthDate, url

}
