package api;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseSpecification {

    public static final String BASE_URL = "https://automationexercise.com";

        protected RequestSpecification getBaseSpec() {
            return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .accept("application/json");
        }
}
