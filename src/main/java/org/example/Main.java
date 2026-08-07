package org.example;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class Main {
    public static void main(String[] args) {
        // 1️⃣ Устанавливаем базовый URL
        RestAssured.baseURI = "https://autotmonexercise.com/api";

        // 2️⃣ Отправляем GET запрос
        given()
                .when()
                .get("/productList")  // 👈 Правильный путь
                .then()
                .log().all()          // 👈 Показываем всё
                .extract()
                .response();          // 👈 Извлекаем ответ
    }
}