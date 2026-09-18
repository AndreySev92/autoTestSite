package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class BlueTopCardPage {

    public static final String BLUE_TOP_ID = "1";
    public static final String BLUE_TOP_NAME = "Blue Top";
    public static final String BLUE_TOP_CAT  = "Women > Tops";


    private final SelenideElement productImage = $(".view-product img");
    private final SelenideElement productTitle = $(".product-information h2");
    private final SelenideElement productCategory = $x("//p[contains(text(), 'Category:')]");


    public BlueTopCardPage checkBlueTopPage() {
        checkUrl(BLUE_TOP_ID);
        checkProductName(BLUE_TOP_NAME);
        checkProductImageIsVisible();
        checkCategory(BLUE_TOP_CAT);
        return this;
    }

    public BlueTopCardPage checkUrl(String productId) {
        webdriver().shouldHave(urlContaining("/product_details/" + productId));
        return this;
    }

    public BlueTopCardPage checkProductName(String expectedName) {
        productTitle.shouldBe(visible).shouldHave(text(expectedName));
        return this;
    }

    public BlueTopCardPage checkProductImageIsVisible() {
        productImage.shouldBe(visible);
        return this;
    }
    public BlueTopCardPage checkCategory(String expectedCategory) {
        productCategory.shouldBe(visible).shouldHave(text(expectedCategory));
        return this;
    }
}
