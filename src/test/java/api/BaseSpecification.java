package api;

import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseSpecification {

    public static final String BASE_URL = "https://automationexercise.com/api";

    static {
        RestAssured.config = RestAssured.config()
                .redirect(RedirectConfig.redirectConfig()
                        .followRedirects(true)
                        .maxRedirects(5));
    }


    protected RequestSpecification getBaseSpec() {
        return given()
                .baseUri(BASE_URL)
                .contentType(io.restassured.http.ContentType.JSON)
                .accept(io.restassured.http.ContentType.JSON);
    }
}
