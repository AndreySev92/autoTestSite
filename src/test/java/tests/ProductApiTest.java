package tests;

import api.ProductServise;
import dto.Brand;
import dto.BrandResponse;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("API тесты для продуктов")
public class ProductApiTest {

    private final ProductServise productServise = new ProductServise();

    @Test
    @DisplayName("Получить список всех брендов")
    void brandListIsNotEmpty() {
        Response response = productServise.getBrandsList();

        assertThat(response.getStatusCode()).isEqualTo(200);

        JsonPath jsonPath = response.jsonPath();

        int responseCode = jsonPath.getInt("responseCode");
        assertThat(responseCode).isEqualTo(200);

        List<Brand> brands = jsonPath.getList("brands", Brand.class);

        assertThat(brands)
                .as("Список не должен быть пустым,каждый должен иметь id + brand, id уникальны")
                .isNotNull()
                .isNotEmpty()
                .allMatch(brand ->
                        brand.getId() != null && brand.getId() > 0 &&
                                brand.getBrand() != null && !brand.getBrand().isEmpty())
                .extracting(Brand::getId)
                .doesNotHaveDuplicates();
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    @DisplayName("Проверка, что бренд с ID существует в списке")
    void brandShouldExistById(int brandId) {
        Response response = productServise.getBrandsList();
        JsonPath jsonPath = response.jsonPath();

        List<Integer> ids = jsonPath.getList("brands.id");

        assertThat(ids)
                .as("Бренд с ID %d должен существовать", brandId)
                .contains(brandId);
    }
    @Test
    @DisplayName("Получить список всех продуктов")
    void productsListIsNotEmpty() {
        Response response = productServise.getProductsList();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

}