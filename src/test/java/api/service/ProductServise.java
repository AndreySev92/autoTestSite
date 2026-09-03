package api.service;

import config.BaseSpecification;
import dto.LoginRequestDto;
import dto.RegisterRequestDto;
import dto.SignupRequestDto;
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
    public static final String REGISTER = "/api/createAccount";
    public static final String DELETE_CART = "/delete_cart";
    public static final String DELETE_USER = "/api/deleteAccount";
    public static final String LOGIN = "/api/verifyLogin";
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
    //поиск товара
    public Response searchProduct(String search) {
        return getBaseSpec()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", search)
                .post(SEARCH)
                .then()
                .parser("text/html", Parser.JSON)
                .extract().response();
    }
    //авторизация
    public Response login(LoginRequestDto request){
        return getBaseSpec()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", request.getEmail())
                .formParam("password", request.getPassword())
                .post(LOGIN)
                .then()
                .parser("text/html", Parser.JSON)
                .extract()
                .response();
    }
    //регистрация пользователя
    public Response register(RegisterRequestDto request) {
        return getBaseSpec()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", request.getName())
                .formParam("firstname", request.getFirstname())
                .formParam("lastname", request.getLastname())
                .formParam("email", request.getEmail())
                .formParam("password", request.getPassword())
                .formParam("address1", request.getAddress1())
                .formParam("country", request.getCountry())
                .formParam("state", request.getState())
                .formParam("city", request.getCity())
                .formParam("zipcode", request.getZipcode())
                .formParam("mobile_number", request.getMobile_number())
                .post(REGISTER)
                .then()
                .parser("text/html", Parser.JSON)
                .extract()
                .response();
    }
    //удаление пользователя
    public Response deleteUser(String email) {
        return getBaseSpec()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .delete(DELETE_USER)
                .then()
                .parser("text/html", Parser.JSON)
                .extract()
                .response();
    }



}
