package testdata.expected;

import dto.Product;

public class ProductTestData {
    public static Product blueTop() {
        return new Product(1, "Blue Top", "Rs. 500", "Polo", "Women", "Tops");
    }

    public static int expectedResponseStatus200 = 200;
}
