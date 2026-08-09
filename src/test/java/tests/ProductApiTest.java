package tests;

import api.ProductServise;
import dto.Brand;
import dto.BrandResponse;
import dto.Product;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("API тесты для продуктов")
public class ProductApiTest {

    private static ProductServise productServise;

    @BeforeAll
    static void setup() {
        productServise = new ProductServise();
    }

//    @Test
//    @DisplayName("Проверка, что ответ содержит список продуктов")
//    void productsListIsNotEmpty() {
//        Response response = productServise.getProductsList();
//
//        assertThat(response.getStatusCode()).isEqualTo(200);
//
//        ProductResponse productResponse = response.as(ProductResponse.class);
//        List<Product> products = productResponse.getProducts();
//
//        assertThat(products)
//                .as("Список продуктов не должен быть пустым")
//                .isNotEmpty();
//
//        assertThat(products)
//                .as("Все продукты должны иметь заполненные поля")
//                .allMatch(product ->
//                        product.getId() != null && product.getId() > 0 &&
//                                product.getName() != null && !product.getName().isEmpty() &&
//                                product.getPrice() != null && !product.getPrice().isEmpty()
//                );
//    }

    @Test
    @DisplayName("Проверка, что ответ содержит список продуктов")
    void productsListIsNotEmpty() {
        Response response = productServise.getProductsList();

        assertThat(response.getStatusCode()).isEqualTo(200);

        // Получаем HTML как строку
        String htmlBody = response.asString();

        // Извлекаем JSON из HTML
        String jsonBody = htmlBody
                .replace("<html>", "")
                .replace("</html>", "")
                .replace("<body>", "")
                .replace("</body>", "")
                .trim();

        // Парсим JSON в список продуктов
        List<Product> products = JsonPath.from(jsonBody).getList("products", Product.class);

        // Проверяем, что список не пустой
        assertThat(products)
                .as("Список продуктов не должен быть пустым")
                .isNotEmpty();

        // Проверяем, что у всех продуктов есть цена
        assertThat(products)
                .as("Все продукты должны иметь цену")
                .allMatch(product -> product.getPrice() != null && !product.getPrice().isEmpty());
    }
    }


    @Test
    @DisplayName("Получить список всех брендов")
    void brandListIsNotEmpty() {
        Response response = productServise.getBrandsList();
        assertThat(response.getStatusCode()).isEqualTo(200);

        BrandResponse brandResponse = response.as(BrandResponse.class);
        List<Brand> brands = brandResponse.getBrands();

        assertThat(brands)
                .as("Список брендов не должен быть пустым")
                .isNotEmpty();
    }

    @Test
    @DisplayName("Проверка наличия конкретных брендов в списке")
    void hasIsCategoryInList() {
        Response response = productServise.getBrandsList();
        assertThat(response.getStatusCode()).isEqualTo(200);

        BrandResponse brandResponse = response.as(BrandResponse.class);
        List<Brand> brands = brandResponse.getBrands();

        List<String> actualBrands = brands.stream()
                .map(Brand::getBrand)
                .collect(Collectors.toList());

        assertThat(actualBrands)
                .as("Список брендов должен содержать: Polo, H&M, Madame, Biba")
                .contains("Polo", "H&M", "Madame", "Biba");
    }

    @Test
    @DisplayName("Проверка что id все уникальны")
    void brandUnique() {
        Response response = productServise.getBrandsList();
        assertThat(response.getStatusCode()).isEqualTo(200);

        BrandResponse brandResponse = response.as(BrandResponse.class);
        List<Brand> brands = brandResponse.getBrands();

        assertThat(brands)
                .extracting(Brand::getId)  // ✅ ИСПРАВЛЕНО! Brand::getId, а не BrandResponse::getId
                .as("Все ID брендов должны быть уникальными")
                .doesNotHaveDuplicates();
    }
}