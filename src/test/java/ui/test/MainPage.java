package ui.test;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final SelenideElement searchButton = $x("submit_search");

    public MainPage(String searchUrl) {
    }

    public void clickSearchButton() {
        searchButton.click();
    }
    
    public void openSearchPage(String url) {

    }

}
