package tests;

import static org.assertj.core.api.Assertions.assertThat;

import api.ProductServise;
import dto.Brand;
import io.restassured.response.Response;

import dto.Product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@DisplayName("API тесты для продуктов")
public class ProductApiTest {

    private static ProductServise productServise;

    @BeforeAll
    static void setup() {
        productServise = new ProductServise();
    }
    @Test
    @DisplayName("Проверка статуса 200")
    void showStatus200(){
        Response response = productServise.getProductsList();

        assertThat(response.getStatusCode()).isEqualTo(200);

    }
    @Test
    @DisplayName("Проверка, что ответ содержит список продуктов")
    void productsListIsNotEmpty(){
        Response response = productServise.getProductsList();

        assertThat(response.getStatusCode()).isEqualTo(200);


        Product[] products = response.as(Product[].class);
        assertThat(products)
                .as("Список продуктов не должен быть пустым")
                .isNotEmpty();

        assertThat(products)
                .as("Все продукты должны иметь заполненные поля")
                .allMatch(product ->
                        product.getId() != null && product.getId() > 0 &&
                                product.getName() != null && !product.getName().isEmpty() &&
                                product.getPrice() != null && !product.getPrice().isEmpty() &&
                                product.getBrand() != null && !product.getBrand().isEmpty() &&
                                product.getCategory() != null
                );
    }

    @Test
    @DisplayName("Получить список всех брендов")
    void brandListIsNotEmpty(){
        Response response = productServise.getProductsList();
        assertThat(response.getStatusCode()).isEqualTo(200);
        Brand[] brands = response.as(Brand[].class);
        assertThat(brands)
                .as("Список брендов не должен быть пустым")
                .isNotEmpty();
    }

    @Test
    @DisplayName("Проверка наличия конкретных брендов в списке")
    void hasIsCategoryInList(){
        Response response = productServise.getProductsList();
        assertThat(response.getStatusCode()).isEqualTo(200);
        Brand[] brands = response.as(Brand[].class);
        List<String> actualBrands = Arrays.stream(brands)
                .map(Brand::getBrand)
                .collect(Collectors.toList());

        assertThat(actualBrands)
                .contains("Polo", "H&M", "Madame", "Biba");
    }

    @Test
    @DisplayName("Проверка что id все уникальны")
    void brandIsUnique(){
        Response response = productServise.getProductsList();
        assertThat(response.getStatusCode()).isEqualTo(200);
        Brand[] brands = response.as(Brand[].class);
        assertThat(brands)
                .extracting(Brand::getId)
                .as("Все ID брендов должны быть уникальными")
                .doesNotHaveDuplicates();

    }


}
