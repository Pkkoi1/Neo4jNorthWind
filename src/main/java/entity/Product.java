package entity;
import lombok.*;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int productID;
    private String productName;
    @ToString.Exclude
    private Supplier supplier;
    @ToString.Exclude
    private Category category;
    private String quantityPerUnit;
    private double unitPrice;
    private int unitsInStock;
    private int unitsOnOrder;
    private int reorderLevel;
    private Boolean discontinued;


}
