package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private Integer id;
    private String name;
    private String price;
    private String brand;
    private Category category;

    public Product(int id, String name, String price, String brand, String userType, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;

        // Создаем вложенные объекты
        this.category = new Category();
        this.category.setUsertype(new UserType());
        this.category.getUsertype().setUsertype(userType);
        this.category.setCategory(categoryName);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Category {
        private UserType usertype;
        private String category;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserType {
        private String usertype;
    }
}