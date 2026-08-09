package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private Integer id;
    private String name;
    private String price;
    private String brand;
    private Category category;

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