package entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
    @SerializedName("categoryID")
    private String id;
    private String categoryName;
    private String description;
    private String picture;
}
