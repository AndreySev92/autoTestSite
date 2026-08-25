package api.tests;

import api.service.ProductServise;
import dto.Product;
import dto.ProductResponseDto;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@DisplayName("API тесты для продуктов")
class ProductApiTest {

    private final ProductServise productServise = new ProductServise();

    @Tag("success")
    @Test
    @DisplayName("1 - GET /api/productsList - возвращает список продуктов с id, name, price, brand + проверка статуса 200")
    void productsListContainsIdNamePriceBrandTest() {
        int expectedResponseStatus200 = 200;

        Response response = productServise.getProducts();

        assertThat(response.statusCode()).isEqualTo(expectedResponseStatus200);
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/products-schema.json"));

    }

    @Test
    @Tag("success")
    @DisplayName("2 - GET /api/brandsList - Получение списка брендов, возвращает статус 200")
    void brandsList200NotEmptyTest() {
        int expectedResponseStatus200 = 200;

        Response response = productServise.getBrandsList();

        assertThat(response.statusCode()).isEqualTo(expectedResponseStatus200);
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/brands-schema.json"));


    }

    @Test
    @Tag("success")
    @DisplayName("3 - POST /api/searchProduct - Получение списка брендов, возвращает статус 200")
    void searchProductTest() {
        int expectedResponseStatus200 = 200;
        String searchTerm = "shirt";


        Response response = productServise.searchProduct(searchTerm);


        // ⭐ ЛОГИРУЕМ
        System.out.println("Status: " + response.statusCode());
        System.out.println("Body: " + response.asString());

        assertThat(response.statusCode()).isEqualTo(expectedResponseStatus200);


        ProductResponseDto productResponseDto = response.as(ProductResponseDto.class);
        List<Product> products = productResponseDto.getProducts();

        assertThat(response.statusCode()).isEqualTo(expectedResponseStatus200);
        assertThat(products)
                .allMatch(product ->
                        product.getName().toLowerCase().contains(searchTerm)
                );
    }

//
//    @Test
//    @Tag("success")
//    @DisplayName("2.1 Список брендов не пустой ")
//    void brandList_IsNotEmpty() {
//        // GIVEN
//        // WHEN
//        Response response = productServise.getBrandsList();
//        BrandResponseDto responseDto = response.as(BrandResponseDto.class);
//        //THEN
//        assertThat(responseDto.getResponseCode()).isEqualTo(200);
//        assertThat(responseDto.getBrands())
//                .as("Список брендов должен содержать элементы")
//                .isNotEmpty()
//                .isNotNull();
//    }
//
//
//    @Test
//    @Tag("validationvalidation")
//    @DisplayName("2.2 Каждый бренд имеет название  и уникальный Id")
//    void brandList_Id_Unique() {
//        // GIVEN
//        // WHEN
//        Response response = productServise.getBrandsList();
//        BrandResponseDto responseDto = response.as(BrandResponseDto.class);
//        List<Brand> brands = responseDto.getBrands();
//        //THEN
//        assertThat(brands)
//                .as("Все бренды должны иметь положительный ID и непустое название")
//                .allMatch(brand->
//                        brand.getId() != null && brand.getId() > 0 &&
//                        brand.getBrand() != null && brand.getBrand().isEmpty()
//                        );
//        assertThat(brands)
//                .as("Id должен быть уникальным")
//                .extracting(Brand::getId)
//                .doesNotHaveDuplicates();
//
//    }
//
//    @Test
//    @Tag("validation")
//    @DisplayName("2.3 Название брендов должны быть уникальными ")
//    void brandList_brand_Unique() {
//        // GIVEN
//        // WHEN
//        Response response = productServise.getBrandsList();
//        //THEN
//        BrandResponseDto responseDto = response.as(BrandResponseDto.class);
//
//        List<Brand> brands = responseDto.getBrands();
//        //THEN
//        assertThat(brands)
//                .as("Название брендов должны быть уникальными")
//                .extracting(Brand::getBrand)
//                .doesNotHaveDuplicates();
//
//    }
//
//
//
//
//
//
//
//
//
//    @Tag("success")
//    @ParameterizedTest
//    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
//    @DisplayName("2.2 Проверка, что бренд с ID существует в списке")
//    void brandShouldExistByIdTest(int brandId) {
//        // GIVEN - ID из параметров
//        // WHEN
//        Response response = productServise.getBrandsList();
//        JsonPath jsonPath = response.jsonPath();
//        // THEN
//        List<Integer> ids = jsonPath.getList("brands.id");
//
//        assertThat(ids)
//                .as("Бренд с ID %d должен существовать", brandId)
//                .contains(brandId);
//    }
//
//
//
//
//    @Tag("success")
//    @ParameterizedTest
//    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
//    @DisplayName("4 Параметризованный, добавление продукта в корзину по ID")
//    void addToCartParamTest(int productId) {
//        // GIVEN - данные из параметров
//        // WHEN
//        Response response = productServise.addToCart(productId);
//        // THEN
//        assertThat(response.getStatusCode())
//                .as("HTTP статус должен быть 200")
//                .isEqualTo(200);
//
//        String body = response.getBody().asString();
//        assertThat(body)
//                .as("Тело ответа должно быть 'Added To Cart'")
//                .isEqualTo("Added To Cart");
//    }
//
//    @Tag("success")
//    @ParameterizedTest
//    @DisplayName("4.1 Проверка, что товар с ID=1 существует")
//    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
//    void productExistTest() {
//        Response response = productServise.getProductsList();
//        assertThat(response.getStatusCode())
//                .as("HTTP статус должен быть 200")
//                .isEqualTo(200);
//
//        JsonPath jsonPath = response.jsonPath();
//
//        int responseCode = jsonPath.getInt("responseCode");
//        assertThat(responseCode)
//                .as("Внутренний responseCode должен быть 200")
//                .isEqualTo(200);
//
//        List<Product> products = response.jsonPath().getList("products", Product.class);
//
//        assertThat(products)
//                .as("Список продуктов не должен быть пустым и должен содержать товар с ID=1")
//                .isNotNull()
//                .isNotEmpty()
//                .anyMatch(product -> product.getId() == 1);
//    }
//
//    @Tag("Validation")
//    @ParameterizedTest
//    @ValueSource(ints = {999999, 0, -1})
//    @DisplayName("4.2 Проверка, что товар с несуществующим ID отсутствует")
//    void notIdpProductTest(int nonExistentId) {
//        // GIVEN - заведомо несуществующий ID
//        // WHEN
//        Response response = productServise.getProductsList();
//
//        // THEN
//        assertThat(response.getStatusCode())
//                .as("HTTP статус должен быть 403")
//                .isEqualTo(200);
//
//        JsonPath jsonPath = response.jsonPath();
//
//        int responseCode = jsonPath.getInt("responseCode");
//        assertThat(responseCode)
//                .as("Внутренний responseCode должен быть 200")
//                .isEqualTo(200);
//
//        List<Product> products = response.jsonPath().getList("products", Product.class);
//
//        assertThat(products)
//                .as("Товар с ID=%d не должен существовать", nonExistentId)
//                .isNotNull()
//                .noneMatch(product -> product.getId() == nonExistentId);
//    }
//
//
//    @Tag("success")
//    @ParameterizedTest
//    @ValueSource(ints = {1, 2, 3, 4, 5})
//    @DisplayName("5. Добавление и удаление товара из корзины")
//    void addAndDeleteProductTest(int productId) {
//        //GIVEN
//        // WHEN - добавляем товар
//        Response addResponse = productServise.addToCart(productId);
//        // THEN - проверяем добавление
//        assertThat(addResponse.getStatusCode())
//                .as("HTTP статус при добавлении товара ID=%d", productId)
//                .isEqualTo(200);
//        assertThat(addResponse.getBody().asString())
//                .as("Тело ответа при добавлении товара ID=%d", productId)
//                .isEqualTo("Added To Cart");
//        // WHEN - удаляем товар
//
//        Response deleteResponse = productServise.deleteFromCart(productId);
//
//        // THEN - проверяем удаление
//        assertThat(deleteResponse.getStatusCode())
//                .as("HTTP статус при удалении товара ID=%d", productId)
//                .isEqualTo(200);
//        assertThat(deleteResponse.getBody().asString())
//                .as("Тело ответа при удалении товара ID=%d", productId)
//                .isEqualTo("Cart removed");
//    }
//
//    @Tag("Validation")
//    @ParameterizedTest
//    @ValueSource(ints = {999999, 0, -1})
//    @DisplayName("5.1 Удаление несуществующего товара из корзины")
//    void deleteNonExistentProductTest(int productId) {
//        // GIVEN - несуществующий ID из параметров
//        // WHEN
//        Response response = productServise.deleteFromCart(productId);
//
//        // THEN
//        if (productId == -1) {
//
//            assertThat(response.getStatusCode())
//                    .as("HTTP статус для ID=%d должен быть 404", productId)
//                    .isEqualTo(404);
//        } else {
//            String body = response.getBody().asString();
//            assertThat(body)
//                    .as("Тело ответа для ID=%d должен быть 200", productId)
//                    .isEqualTo("Cart removed");
//
//
//        }
//    }
//
//


}