package tests;

import api.ProductServise;
import dto.Brand;
import dto.Product;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.List;
import java.util.Map;

import static api.BaseSpecification.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("API тесты для продуктов")
class ProductApiTest {

    private final ProductServise productServise = new ProductServise();

    private JsonPath validateResponse(Response response){

        assertThat(response.getStatusCode()).isEqualTo(200);

        JsonPath jsonPath = response.jsonPath();

        int responseCode = jsonPath.getInt("responseCode");
        assertThat(responseCode).isEqualTo(200);
        return jsonPath;
    }


    @Test
    @DisplayName("Получить список всех брендов")
    void brandListIsNotEmpty() {
        Response response = productServise.getBrandsList();

        validateResponse(response);

        List<Brand> brands = response.jsonPath().getList("brands", Brand.class);

        assertThat(brands)
                .as("Проверка на null и пустой ответ,должен иметь id + brand, уникальный id")
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

        validateResponse(response);

        List<Product> products = response.jsonPath().getList("products", Product.class);
        assertThat(products)
                .as("Проверка на null и пустой ответ,должен иметь id + brand, уникальный id")
                .isNotNull()
                .isNotEmpty()
                .allMatch(product ->
                        product.getId() != null && product.getId() > 0 &&
                                product.getBrand() != null && !product.getBrand().isEmpty())
                .extracting(Product::getId)
                .doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Вход в аккаунт с валидными данными Email, Password")
    void loginTest() {
        Response response = productServise.login("max@mail.ru", "123123");

        JsonPath jsonPath = validateResponse(response);

        assertThat(jsonPath.getString("message"))
                .isEqualTo("User exists!");
    }




}