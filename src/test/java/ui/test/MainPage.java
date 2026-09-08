package ui.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final SelenideElement searchInput = $("#search_product");
    private final SelenideElement searchButton = $("#submit_search");
    private final SelenideElement settingsButton = $("button.fc-cta-manage-options");
    private final SelenideElement acceptAllButton = $x("//p[@class='fc-button-label' and contains(text(), 'Принять все')]");

    private final String baseUrl;

    public MainPage(String url) {
        this.baseUrl = url;
        Selenide.open(url);
    }

    public MainPage open() {
        Selenide.open(baseUrl);
        return this;
    }

    public MainPage closeCheckCookies(){
        settingsButton.shouldBe(visible).click();
        acceptAllButton.shouldBe(visible).click();
    return this;
    }

    public MainPage search(String query) {
        searchInput.shouldBe(visible).setValue(query);
        searchButton.click();
        return this;
    }

}
