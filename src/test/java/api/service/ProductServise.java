package api.service;

import config.BaseSpecification;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class ProductServise extends BaseSpecification {
    public static final String PRODUCTS_LIST = "/api/productsList";
    public static final String BRANDS_LIST = "/api/brandsList";
    public static final String SEARCH = "/api/searchProduct";
    public static final String ADD_TO_CART = "/add_to_cart";
    public static final String VIEW_CART = "/view_cart";
    public static final String DELETE_CART = "/delete_cart";
    public static final String LOGIN = "/verifyLogin";
    private static final String GET_USER_DETAIL = "/getUserDetailByEmail";

    // Получить список продуктов
    public Response getProducts(){
        return getBaseSpec()
                .get(PRODUCTS_LIST)
                .then()
                .parser("text/html", Parser.JSON)
                .extract().response();

    }

    //получение списка брэндов
    public Response getBrandsList(){
        return getBaseSpec()
                .get(BRANDS_LIST)
                .then()
                .parser("text/html", Parser.JSON)
                .extract().response();
    }

    public Response searchProduct(String search) {
        return getBaseSpec()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", search)
                .post(SEARCH)
                .then()
                .parser("text/html", Parser.JSON)
                .extract().response();
    }
    //Вход в аккаунт
//    public Response login(UserCred request) {
//        return getBaseSpec()
//                .post(LOGIN);
//    }


    // Добавить продукт в корзину
    public Response addToCart(int productId) {

        return given()
                .get(ADD_TO_CART + "/" + productId)
                ;
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
