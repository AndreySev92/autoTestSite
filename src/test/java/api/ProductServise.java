package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class ProductServise extends BaseSpecification {
    public static final String PRODUCTS_LIST = "/api/productsList";
    public static final String BRANDS_LIST = "/brandsList";
    public static final String ADD_TO_CART = "/add_to_cart";
    public static final String VIEW_CART = "/view_cart";
    public static final String DELETE_CART = "/delete_cart";
    public static final String LOGIN = "/verifyLogin";
    private static final String GET_USER_DETAIL = "/getUserDetailByEmail";


    // Получить список продуктов
    public Response getProductsList(){
        return getBaseSpec()
                .get(PRODUCTS_LIST);

    }
    //получение списка брэндов
    public Response getBrandsList(){
        return getBaseSpec()
                .get(BRANDS_LIST);
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
