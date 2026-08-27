package api.tests;

import api.service.ProductServise;
import com.github.javafaker.Faker;
import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testdata.TestDataGenerator;


import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static testdata.expected.ExpectedResponse.expectedResponseStatus200;

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

        assertThat(response.statusCode()).isEqualTo(expectedResponseStatus200);
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

        assertThat(response.statusCode()).isEqualTo(expectedResponseStatus200);
        assertThat(names).allMatch(name -> name.contains(searchTerm) || categories.get(names.indexOf(name)).contains(searchTerm));
    }



    @Test
    @Tag("validation")
    @DisplayName("API - 004 - POST /api/searchProduct - Запрос не существующего товара")
    void productNotFoundTest() {
        String searchTerm = "nonexistentitemxyz";

        Response response = productServise.searchProduct(searchTerm);
        ProductResponseDto productDto = response.as(ProductResponseDto.class);

        assertThat(response.statusCode()).isEqualTo(expectedResponseStatus200);
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

    @Test
    @Tag("validation")
    @DisplayName("API - 006 - POST /api/verifyLogin - авторизация без Email")
    void loginWithoutEmailTest() {
        RegisterRequestDto registerRequest = TestDataGenerator.generateRegisterRequest();
        productServise.register(registerRequest);

        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .password(registerRequest.getPassword())
                .build();

        Response response = productServise.login(loginRequest);
        LoginResponseDto actual = response.as(LoginResponseDto.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(actual.getMessage()).contains("User not found");

        productServise.deleteUser(registerRequest.getEmail());

    }

    @Test
    @Tag("validation")
    @DisplayName("API - 007 - POST /api/verifyLogin - авторизация с неверным Email и Password")
    void loginWithInvalidEmailAndPassTest() {
        RegisterRequestDto registerRequest = TestDataGenerator.generateRegisterRequest();
        productServise.register(registerRequest);

        Faker faker = new Faker();
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();

        Response response = productServise.login(loginRequest);
        LoginResponseDto actual = response.as(LoginResponseDto.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(actual.getMessage()).contains("User not found!");

    }


}