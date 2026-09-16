package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class CategoryProductDressPage {

    private final SelenideElement pageTitle = $("h2.title.text-center");
    private final SelenideElement productsContainer = $(".features_items");


    public CategoryProductDressPage checkUrl() {
        webdriver().shouldHave(urlContaining("/category_products/1"));
        return this;
    }

    public CategoryProductDressPage checkTitle() {
        pageTitle.shouldBe(visible).shouldHave(text("Women - Dress Products"));
        return this;
    }





}
