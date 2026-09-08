package ui.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;


public class AutomatSiteTest extends BaseTest {
    public static final String SEARCH_STRING = "Madame Top For Women";
    public static final String SEARCH_URL = "https://automationexercise.com/products";



    @Test
    public void searchTest(){
        Selenide.open(SEARCH_URL);
        $("search_product").setValue(SEARCH_STRING);
        $("submit_search").click();
    }


}
