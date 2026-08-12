package api;

import io.restassured.RestAssured;
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
    private static final String GET_USER_DETAIL = "/getUserDetailByEmail";


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
                .accept(ContentType.JSON)
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(LOGIN)
                .then()
                .extract()
                .response();
    }


    // Добавить продукт в корзину
    public Response addToCart(int productId) {

        return given()
                .baseUri("https://automationexercise.com")
                .contentType(ContentType.URLENC)
                .accept(ContentType.JSON)
                .header("Referer", "https://automationexercise.com/")
                .when()
                .get(ADD_TO_CART + "/" + productId)
                .then()
                .extract()
                .response();
    }


    // Удалить продукт из корзины
    public Response deleteFromCart(int productId) {
        return getBaseSpec()
                .baseUri("https://automationexercise.com")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Referer", "https://automationexercise.com/")
                .when()
                .get(DELETE_CART + "/" + productId)
                .then()
                .extract()
                .response();
    }

    // Получение информации о пользователе
    public Response getUserDetail(String email) {
        return getBaseSpec()
                .queryParam("email", email)
                .when()
                .get(GET_USER_DETAIL)
                .then()
                .extract()
                .response();
    }



}
