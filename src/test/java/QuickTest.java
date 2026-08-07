

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class QuickTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://automationexercise.com/api";

        given()
                .when()
                .get("/productsList")
                .then()
                .log().all()
                .statusCode(200)          // 👈 ПРОВЕРЯЕТ, ЧТО СТАТУС 200
                .extract()
                .response();
    }
}