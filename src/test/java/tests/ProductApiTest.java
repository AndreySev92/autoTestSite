package tests;

import api.ProductServise;
import dto.Brand;
import dto.LoginResponseDto;
import dto.Product;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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

//    @BeforeEach
//    void setUp() {
//        RestAssured.config = RestAssured.config()
//                .redirect(RedirectConfig.redirectConfig()
//                        .followRedirects(true)
//                        .maxRedirects(5));
//
//    }

    @Test
    @Tag("success")
    @DisplayName("1.Вход с валидными данными Email и Password")
    void loginTest() {
        // GIVEN
        String email = "max@mail.ru";
        String password = "123123";

        // WHEN
        Response response = productServise.login(email, password);

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(200);

        LoginResponseDto loginResponse = response.as(LoginResponseDto.class);

        assertThat(loginResponse.getResponseCode()).isEqualTo(200);
        assertThat(loginResponse.getMessage()).isEqualTo("User exists!");
    }

//    @Tag("validation")
//    @ParameterizedTest
//    @MethodSource("providers.LoginDataProvider#provideLoginData")
//    @DisplayName("1.1 Вход в аккаунт с невалидными данными (ошибки валидации)")
//    void loginValidationTest(LoginTestData loginTestData) {
//        if (loginTestData.getResultType() == LoginTestData.TestResultType.SUCCESS) {
//            return;
//        }
//        // GIVEN - данные из провайдера
//        // WHEN
//        Response response = productServise.login(loginTestData.getEmail(), loginTestData.getPassword());
//        // THEN
//        assertThat(response.getStatusCode())
//                .as("HTTP статус для '%s'", loginTestData.getEmail())
//                .isEqualTo(200);
//
//        JsonPath jsonPath = response.jsonPath();
//
//        assertThat(jsonPath.getMap(""))
//                .as("Проверка responseCode и message для '%s'", loginTestData.getEmail())
//                .extracting("responseCode", "message")
//                .containsExactly(404, loginTestData.getExpectedMessage());
//    }

    @Tag("success")
    @Test
    @DisplayName("2.Получить список всех брендов")
    void brandListIsNotEmptyTest() {
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

    @Tag("success")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    @DisplayName("2.2 Проверка, что бренд с ID существует в списке")
    void brandShouldExistByIdTest(int brandId) {
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
    void productsListIsNotEmptyTest() {
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
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    @DisplayName("4 Параметризованный, добавление продукта в корзину по ID")
    void addToCartParamTest(int productId) {
        // GIVEN - данные из параметров
        // WHEN
        Response response = productServise.addToCart(productId);
        // THEN
        assertThat(response.getStatusCode())
                .as("HTTP статус должен быть 200")
                .isEqualTo(200);

        String body = response.getBody().asString();
        assertThat(body)
                .as("Тело ответа должно быть 'Added To Cart'")
                .isEqualTo("Added To Cart");
    }

    @Tag("success")
    @ParameterizedTest
    @DisplayName("4.1 Проверка, что товар с ID=1 существует")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    void productExistTest() {
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
                .as("Список продуктов не должен быть пустым и должен содержать товар с ID=1")
                .isNotNull()
                .isNotEmpty()
                .anyMatch(product -> product.getId() == 1);
    }

    @Tag("Validation")
    @ParameterizedTest
    @ValueSource(ints = {999999, 0, -1})
    @DisplayName("4.2 Проверка, что товар с несуществующим ID отсутствует")
    void notIdpProductTest(int nonExistentId) {
        // GIVEN - заведомо несуществующий ID
        // WHEN
        Response response = productServise.getProductsList();

        // THEN
        assertThat(response.getStatusCode())
                .as("HTTP статус должен быть 403")
                .isEqualTo(200);

        JsonPath jsonPath = response.jsonPath();

        int responseCode = jsonPath.getInt("responseCode");
        assertThat(responseCode)
                .as("Внутренний responseCode должен быть 200")
                .isEqualTo(200);

        List<Product> products = response.jsonPath().getList("products", Product.class);

        assertThat(products)
                .as("Товар с ID=%d не должен существовать", nonExistentId)
                .isNotNull()
                .noneMatch(product -> product.getId() == nonExistentId);
    }


    @Tag("success")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("5. Добавление и удаление товара из корзины")
    void addAndDeleteProductTest(int productId) {
        //GIVEN
        // WHEN - добавляем товар
        Response addResponse = productServise.addToCart(productId);
        // THEN - проверяем добавление
        assertThat(addResponse.getStatusCode())
                .as("HTTP статус при добавлении товара ID=%d", productId)
                .isEqualTo(200);
        assertThat(addResponse.getBody().asString())
                .as("Тело ответа при добавлении товара ID=%d", productId)
                .isEqualTo("Added To Cart");
        // WHEN - удаляем товар

        Response deleteResponse = productServise.deleteFromCart(productId);

        // THEN - проверяем удаление
        assertThat(deleteResponse.getStatusCode())
                .as("HTTP статус при удалении товара ID=%d", productId)
                .isEqualTo(200);
        assertThat(deleteResponse.getBody().asString())
                .as("Тело ответа при удалении товара ID=%d", productId)
                .isEqualTo("Cart removed");
    }

    @Tag("Validation")
    @ParameterizedTest
    @ValueSource(ints = {999999, 0, -1})
    @DisplayName("5.1 Удаление несуществующего товара из корзины")
    void deleteNonExistentProductTest(int productId) {
        // GIVEN - несуществующий ID из параметров
        // WHEN
        Response response = productServise.deleteFromCart(productId);

        // THEN
        if (productId == -1) {

            assertThat(response.getStatusCode())
                    .as("HTTP статус для ID=%d должен быть 404", productId)
                    .isEqualTo(404);
        } else {
            String body = response.getBody().asString();
            assertThat(body)
                    .as("Тело ответа для ID=%d должен быть 200", productId)
                    .isEqualTo("Cart removed");


        }
    }



}