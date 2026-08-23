package testdata.expected;

import dto.Product;
import java.util.List;

public class ExpectedResponse {

    private static List<Product> getExpectedProducts(){
                    return List.of(
                            new Product(1, "Blue Top", "Rs. 500", "Polo", "Women", "Tops"));
    }

}

