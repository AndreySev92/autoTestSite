package ui.test;

import org.junit.jupiter.api.Test;

public class AutomatSiteTest extends BaseTest {
    public static final String SEARCH_STRING = "Madame Top For Women";
    public static final String SEARCH_URL = "https://automationexercise.com/products";



    @Test
    public void searchTest(){
        MainPage mainPage = new MainPage(SEARCH_URL);

        mainPage
                .open()
                .closeCheckCookies()
                .search(SEARCH_STRING);
    }


}
