package testdata.builders;

import dto.Product;

public class ProductBuilder {

    private final Product product = new Product();

    public ProductBuilder withId(int id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder withPrice(String price) {
        product.setPrice(price);
        return this;
    }

    public ProductBuilder withBrand(String brand) {
        product.setBrand(brand);
        return this;
    }

    public ProductBuilder withCategory(String category, String userType) {
        Product.Category categoryObj = new Product.Category();
        Product.UserType userTypeObj = new Product.UserType();
        userTypeObj.setUsertype(userType);
        categoryObj.setUsertype(userTypeObj);
        categoryObj.setCategory(category);
        product.setCategory(categoryObj);
        return this;
    }

    public Product build() {
        return product;
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }
}