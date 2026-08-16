package api;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseSpecification {

    public static final String BASE_URL = "https://automationexercise.com/api";

        protected RequestSpecification getBaseSpec() {
            return given()
                .baseUri(BASE_URL)
                    .baseUri(BASE_URL)
                    .header("User-Agent", "PostmanRuntime/7.53.0")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Connection", "keep-alive")
                    .header("Cache-Control", "no-cache")
                .log().all();

        }
}
