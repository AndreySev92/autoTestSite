package ui.test;

import com.codeborne.selenide.Selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


abstract class BaseTest {

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 10000;
    }
    @Test
    public void test(){
        Selenide.open("https://automationexercise.com/products");
    }

    @AfterAll
    public static void tearDown(){
        Selenide.closeWebDriver();
    }

}
