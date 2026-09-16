package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class AccountCreatedPage {

    private final SelenideElement title          = $("[data-qa='account-created']");
    private final SelenideElement continueButton = $("[data-qa='continue-button']");

    public AccountCreatedPage checkAccountCreated() {
        title.shouldBe(visible).shouldHave(text("Account Created!"));
        return this;
    }

    public MainPage clickContinue() {
        continueButton.shouldBe(visible).click();
        return new MainPage();
    }

}
