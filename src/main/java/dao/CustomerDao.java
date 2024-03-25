package dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerDao {
    Driver driver;

    public CustomerDao() {
        driver = utils.AppUtils.driver();
    }

    public void close() {
        driver.close();
    }

    public Map<String, Integer> getNumberCustomerByCity()
    {
        String query = "MATCH (c:Customer) RETURN c.city as city, count(c) as number ORDER BY number DESC";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
               if(!result.hasNext())
               {
                   return null;
               }
               return result.stream()
                       .collect(Collectors.toMap(
                               record -> record.get("city").asString(),
                               record -> record.get("number").asInt(),
                               (a, b) -> a,
                               LinkedHashMap::new
                       ));
            });
            }
        }
}
