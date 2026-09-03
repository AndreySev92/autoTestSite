package api.tests;

import api.service.ProductServise;
import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import testdata.builders.TestDataGenerator;


import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@DisplayName("API тесты для продуктов")
class ProductApiTest {

    private final ProductServise productServise = new ProductServise();

    @Test
    @Tag("success")
    @DisplayName("API - 001 - GET /api/productsList - возвращает список продуктов с id, name, price, brand + проверка статуса 200")
    void productsListContainsIdNamePriceBrandTest() {
        int expectedResponseStatus200 = 200;

        Response response = productServise.getProducts();

        assertThat(response.statusCode()).isEqualTo(expectedResponseStatus200);
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/products-schema.json"));

    }

    @Test
    @Tag("success")
    @DisplayName("API - 002 - GET /api/brandsList - Получение списка брендов, возвращает статус 200")
    void brandsList200NotEmptyTest() {

        Response response = productServise.getBrandsList();

        assertThat(response.statusCode()).isEqualTo(200);
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/brands-schema.json"));


    }

    @ParameterizedTest
    @Tag("validation")
    @CsvSource({"shirt", "top", "dress"})
    @DisplayName("API - 003 - POST /api/searchProduct - Поиск продуктов, возвращает статус 200")
    void searchProductTest(String searchTerm) {

        Response response = productServise.searchProduct(searchTerm);
        ProductResponseDto productDto = response.as(ProductResponseDto.class);
        List<Product> products = productDto.getProducts();
        List<String> names = products.stream().map(p -> p.getName().toLowerCase()).toList();
        List<String> categories = products.stream().map(p -> p.getCategory().getCategory().toLowerCase()).toList();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(names).allMatch(name -> name.contains(searchTerm) || categories.get(names.indexOf(name)).contains(searchTerm));
    }



    @Test
    @Tag("validation")
    @DisplayName("API - 004 - POST /api/searchProduct - Запрос не существующего товара")
    void productNotFoundTest() {
        String searchTerm = "nonexistentitemxyz";

        Response response = productServise.searchProduct(searchTerm);
        ProductResponseDto productDto = response.as(ProductResponseDto.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(productDto.getProducts()).isEmpty();
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/search-product-empty-schema.json"));
    }

    @Test
    @Tag("success")
    @DisplayName("API - 005 - POST /api/verifyLogin - успешная авторизация")
    void loginValidCredentialsTest() {
        RegisterRequestDto registerRequest = TestDataGenerator.generateRegisterRequest();
        productServise.register(registerRequest);

        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();

        LoginResponseDto expected = LoginResponseDto.builder()
                .responseCode(200)
                .message("User exists!")
                .build();

        Response response = productServise.login(loginRequest);
        LoginResponseDto actual = response.as(LoginResponseDto.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(actual).isEqualTo(expected);

        productServise.deleteUser(registerRequest.getEmail());
    }


@ParameterizedTest
@Tag("validation")
@MethodSource("testdata.builders.LoginWithoutEmailDataProvider#loginWithoutEmailDataProvider")
@DisplayName("API - 006 - POST /api/verifyLogin - авторизация без Email / без Password")
void loginWithoutEmailTest(LoginRequestDto loginRequest) {
    Response response = productServise.login(loginRequest);
    LoginResponseDto actual = response.as(LoginResponseDto.class);

    LoginResponseDto expected = LoginResponseDto.builder()
            .responseCode(404)
            .message("User not found!")
            .build();

    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(actual).isEqualTo(expected);
}

    @ParameterizedTest
    @Tag("validation")
    @MethodSource("testdata.builders.LoginWithInvalidEmailAndPassTest#invalidLoginDataProvider")
    @DisplayName("API - 007 - POST /api/verifyLogin - авторизация с неверным Email / Password")
    void loginWithInvalidEmailAndPassTest(LoginRequestDto loginRequest) {
        Response response = productServise.login(loginRequest);
        LoginResponseDto actual = response.as(LoginResponseDto.class);

        LoginResponseDto expected = LoginResponseDto.builder()
                .responseCode(404)
                .message("User not found!")
                .build();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(actual).isEqualTo(expected);
    }


}