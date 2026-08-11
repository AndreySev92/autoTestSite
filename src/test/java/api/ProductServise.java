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
    private static final String SEARCH_PRODUCT = "/searchProduct";

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
//        String csrfToken = getCsrfToken();

        // 1. Отправляем запрос на добавление
        return given()
                .baseUri("https://automationexercise.com")
                .contentType(ContentType.URLENC)
                .accept(ContentType.JSON)
                .header("Referer", "https://automationexercise.com/")
//                .header("X-CSRFToken", csrfToken)
//                .cookie("csrftoken", csrfToken)
//                .formParam("product_id", productId)
                .when()
                .get(ADD_TO_CART + "/" + productId)  // /add_to_cart/1
                .then()
                .extract()
                .response();
//
//        // 2. Если редирект - идем по нему
//        if (response.getStatusCode() == 302) {
//            String location = response.getHeader("Location");
//            System.out.println("Редирект на: " + location);
//
//            // 3. Переходим по ссылке редиректа
//            return given()
//                    .baseUri("https://automationexercise.com")
//                    .when()
//                    .get("/view_cart")
//                    .then()
//                    .extract()
//                    .response();
//        }
//
//        return response;
    }


//    private String sessionId;
//
//    private String getCsrfToken() {
//        Response response = given()
//                .baseUri("https://automationexercise.com")
//                .when()
//                .get("/")
//                .then()
//                .extract()
//                .response();
//
//        sessionId = response.getCookie("sessionid");
//        return response.getCookie("csrftoken");
//    }

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
