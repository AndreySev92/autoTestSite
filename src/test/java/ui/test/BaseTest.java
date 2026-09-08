package ui.test;

import com.codeborne.selenide.Selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static ui.test.AutomatSiteTest.SEARCH_STRING;


abstract class BaseTest {

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 100000;
    }

    @AfterAll
    public static void tearDown(){
        Selenide.closeWebDriver();
    }

}
