package tests;

import api.ProductServise;
import dto.Brand;
import dto.LoginTestData;
import dto.Product;
import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@DisplayName("API тесты для продуктов")
class ProductApiTest {

    private final ProductServise productServise = new ProductServise();

    @BeforeEach
    void setUp() {
        RestAssured.config = RestAssured.config()
                .redirect(RedirectConfig.redirectConfig()
                        .followRedirects(true)
                        .maxRedirects(5));

    }


    @Tag("success")
    @ParameterizedTest
    @MethodSource("providers.LoginDataProvider#provideLoginData")
    @DisplayName("1.Вход в аккаунт с валидными данными Email, Password")
    void loginTest(LoginTestData loginTestData) {
        // GIVEN - данные из провайдера
        // WHEN
        Response response = productServise.login(loginTestData.getEmail(), loginTestData.getPassword());

        // THEN
        assertThat(response.getStatusCode())
                .as("HTTP статус кода для '%s'", loginTestData.getEmail())
                .isEqualTo(loginTestData.getExpectedStatusCode());

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("message"))
                .as("Сообщение для '%s'", loginTestData.getEmail())
                .isEqualTo(loginTestData.getExpectedMessage());
    }

    @Tag("success")
    @Test
    @DisplayName("2.Получить список всех брендов")
    void brandListIsNotEmpty() {
        // GIVEN
        // WHEN
        Response response = productServise.getBrandsList();
        // THEN
        assertThat(response.getStatusCode())
                .as("HTTP статус должен быть 200")
                .isEqualTo(200);

        JsonPath jsonPath = response.jsonPath();

        int responseCode = jsonPath.getInt("responseCode");
        assertThat(responseCode)
                .as("Внутренний responseCode должен быть 200")
                .isEqualTo(200);

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
    @DisplayName("2.2 Проверка, что бренд с ID существует в списке")
    void brandShouldExistById(int brandId) {
        // GIVEN - ID из параметров
        // WHEN
        Response response = productServise.getBrandsList();
        JsonPath jsonPath = response.jsonPath();
        // THEN
        List<Integer> ids = jsonPath.getList("brands.id");

        assertThat(ids)
                .as("Бренд с ID %d должен существовать", brandId)
                .contains(brandId);
    }
    @Tag("success")
    @Test
    @DisplayName("3.Получить список всех продуктов")
    void productsListIsNotEmpty() {
        // GIVEN
        // WHEN
        Response response = productServise.getProductsList();
        // THEN
        assertThat(response.getStatusCode())
                .as("HTTP статус должен быть 200")
                .isEqualTo(200);

        JsonPath jsonPath = response.jsonPath();

        int responseCode = jsonPath.getInt("responseCode");
        assertThat(responseCode)
                .as("Внутренний responseCode должен быть 200")
                .isEqualTo(200);

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

    @Tag("success")
    @Test
    @DisplayName("4. Добавление продукта в корзину по ID")
    void addToCartTest() {
        // GIVEN
        int productId = 1;
        //WHEN
        Response response = productServise.addToCart(productId);

        System.out.println("=== ДЕБАГ ===");
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody().asString());
        System.out.println("============");
        // 👇 ЛОГИРОВАНИЕ ДЛЯ ОТЛАДКИ
        System.out.println("=== ДЕБАГ ДОБАВЛЕНИЯ В КОРЗИНУ ===");
        System.out.println("Product ID: " + productId);
        System.out.println("HTTP Status: " + response.getStatusCode());
        System.out.println("Content-Type: " + response.getContentType());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("=====================================");

        //THEN
        assertThat(response.getStatusCode())
                .as("HTTP статус должен быть 200")
                .isEqualTo(200);

        JsonPath jsonPath = response.jsonPath();

        int responseCode = jsonPath.getInt("responseCode");
        assertThat(responseCode)
                .as("Внутренний responseCode должен быть 200")
                .isEqualTo(200);

        String message = jsonPath.getString("message");
        assertThat(message)
                .as("Сообщение должно быть 'Added To Cart'")
                .isEqualTo("Added To Cart");
    }

    @Test
    @DisplayName("Проверка, что товар с ID=1 существует")
    void productShouldExist() {
        Response response = productServise.getProductsList();
        assertThat(response.getStatusCode())
                .as("HTTP статус должен быть 200")
                .isEqualTo(200);

        JsonPath jsonPath = response.jsonPath();

        int responseCode = jsonPath.getInt("responseCode");
        assertThat(responseCode)
                .as("Внутренний responseCode должен быть 200")
                .isEqualTo(200);

        List<Product> products = response.jsonPath().getList("products", Product.class);

        assertThat(products)
                .as("Товар с ID=1 должен существовать")
                .anyMatch(product -> product.getId() == 1);
    }

    @Tag("success")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    @DisplayName("4.1 Параметризованный.Добавление продукта в корзину по ID")
    void addToCartParamTest() {
        // GIVEN
        int productId = 1;
        //WHEN
        Response response = productServise.addToCart(productId);
        //THEN
        assertThat(response.getStatusCode())
                .as("HTTP статус должен быть 200")
                .isEqualTo(200);

        JsonPath jsonPath = response.jsonPath();

        int responseCode = jsonPath.getInt("responseCode");
        assertThat(responseCode)
                .as("Внутренний responseCode должен быть 200")
                .isEqualTo(200);

        String message = jsonPath.getString("message");
        assertThat(message)
                .as("Сообщение должно быть 'Added To Cart'")
                .isEqualTo("Added To Cart");
    }


}