package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AccountDeletedPage {

    private final SelenideElement title          = $("[data-qa='account-deleted']");
    private final SelenideElement continueButton = $("[data-qa='continue-button']");

    public AccountDeletedPage checkAccountDeleted() {
        title.shouldBe(visible).shouldHave(text("Account Deleted!"));
        return this;
    }

    public MainPage clickContinue() {
        continueButton.shouldBe(visible).click();
        return new MainPage();
    }
}