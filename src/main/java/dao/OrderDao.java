package dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderDao {
    Driver driver;

    public OrderDao() {
        driver = utils.AppUtils.driver();
    }

    public void close() {
        driver.close();
    }

    //Tính tonng63 tiền đơn hàng khi biết mã đơn
    public Double totalOrder(String id) {
        String query = "MATCH (o:Order {orderID: $id}) - [r:ORDERS] -> (p:Product) RETURN sum(toInteger(p.unitPrice) * r.quantity) as total";
        Map<String, Object> params = Map.of("id", id);
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                if(!result.hasNext())
                {
                    return null;
                }
                return result.single().get("total").asDouble();
            });
        }
    }
//    Tính tổng tiền của tấtca3a3 hóa dôđơn trong ngày nào đó
public double totalAmountOfAnOrderByOrderDate(String orderDate) {
    String query = "MATCH (o:Order)-[r:ORDERS]->(p:Product) where o.orderDate= datetime($date) RETURN sum(toFloat(r.unitPrice)*r.quantity) as totalAmount";
    Map<String, Object> pars = Map.of("date", orderDate);

    try (Session session = driver.session()) {
        return session.executeRead(tx -> {
            Result result = tx.run(query, pars);
            if (!result.hasNext())
                return 0.0;
            return result.single().get("totalAmount").asDouble();
        });
    }
}
//    Tính tổng tiền của tất cả hóa đơn trong tháng nào đó
    public double totalAmountOfAnOrderByMonth(int month, int year) {
        String query = "MATCH (o:Order)-[r:ORDERS]->(p:Product) where o.orderDate.month= $month and o.orderDate.year = $year RETURN sum(toFloat(r.unitPrice)*r.quantity) as totalAmount";
        Map<String, Object> pars = Map.of("month", month, "year", year);
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, pars);
                if (!result.hasNext())
                    return 0.0;
                return result.single().get("totalAmount").asDouble();
            });
        }
    }
}
