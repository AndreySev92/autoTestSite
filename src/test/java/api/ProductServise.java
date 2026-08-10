package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductServise extends BaseSpecification {
    public static final String PRODUCTS_LIST = "/productsList";
    public static final String BRANDS_LIST = "/brandsList";
    public static final String ADD_TO_CART = "/add_to_cart";
    public static final String VIEW_CART = "/view_cart";
    public static final String DELETE_CART = "/delete_cart";
    public static final String LOGIN = "/verifyLogin";

    // Получить список продуктов
    public Response getProductsList(){
        return getBaseSpec()
                .when()
                .get(PRODUCTS_LIST)
                .then()
                .extract()
                .response();
    }
    //получение списка брэндов
    public Response getBrandsList(){
        return getBaseSpec()
                .when()
                .get(BRANDS_LIST)
                .then()
                .extract()
                .response();
    }
    //Вход в аккаунт
    public Response login(String email, String password) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.URLENC)
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(LOGIN)
                .then()
                .extract()
                .response();
    }

    public Response searchProduct(String search){
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.URLENC)
                .formParam("search_product", search)
                .when()
                .get("/searchProduct")
                .then()
                .extract()
                .response();
    }

    // Добавить продукт в корзину
    public Response addToCart(int productId) {
        return getBaseSpec()
                .queryParam("product_id", productId)
                .when()
                .post(ADD_TO_CART + "/" + productId)
                .then()
                .extract()
                .response();
    }

    // Посмотреть корзину
    public Response viewCart() {
        return getBaseSpec()
                .when()
                .get(VIEW_CART)
                .then()
                .extract()
                .response();
    }

    // Удалить продукт из корзины
    public Response deleteFromCart(int productId) {
        return getBaseSpec()
                .when()
                .delete(DELETE_CART + "/" + productId)
                .then()
                .extract()
                .response();
    }



}
