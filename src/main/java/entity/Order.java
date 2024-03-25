package entity;
import lombok.*;
import org.neo4j.driver.internal.value.LocalDateTimeValue;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
private int orderID;
    private String customerID;
    private int employeeID;
    private LocalDate orderDate;
    private String requiredDate;
    private String shippedDate;
    private int shipVia;
    private double freight;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
    private String shipPostalCode;
    private String shipCountry;
}
