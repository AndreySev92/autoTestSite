package config;

import com.codeborne.selenide.Selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;



public abstract class BaseUiSpec {

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 100000;
        Configuration.baseUrl = BaseSpecification.BASE_URL;

    }

    @AfterEach
    public void tearDown(){
        Selenide.closeWebDriver();
    }

}
