package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests {

    private static final String BASE_URL = "https://automationexercise.com/api";

    @Test
    void testCreateAccount() {
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "TestUser");
        userData.put("email", "api.test." + System.currentTimeMillis() + "@example.com");
        userData.put("password", "Password123!");
        userData.put("title", "Mr");
        userData.put("birth_date", "15");
        userData.put("birth_month", "3");
        userData.put("birth_year", "1990");
        userData.put("firstname", "John");
        userData.put("lastname", "Doe");
        userData.put("company", "TestCompany");
        userData.put("address1", "123 Main St");
        userData.put("address2", "Apt 4");
        userData.put("country", "Canada");
        userData.put("zipcode", "M5V 2H1");
        userData.put("state", "Ontario");
        userData.put("city", "Toronto");
        userData.put("mobile_number", "1234567890");

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(userData)
                .when()
                .post(BASE_URL + "/createAccount")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertEquals(200, response.statusCode());
        System.out.println("Response: " + response.asString());
    }

    @Test
    void testGetProductsList() {
        Response response = given()
                .when()
                .get(BASE_URL + "/productsList")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertEquals(200, response.statusCode());
        System.out.println("Products: " + response.asString());
    }
}