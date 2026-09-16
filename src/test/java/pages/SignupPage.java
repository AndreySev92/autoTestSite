package pages;

import com.codeborne.selenide.SelenideElement;
import ui.dto.UserData;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class SignupPage {
    private final SelenideElement nameInput    = $("[data-qa='signup-name']");
    private final SelenideElement emailInput   = $("[data-qa='signup-email']");
    private final SelenideElement signupButton = $("[data-qa='signup-button']");

    public RegistrationPage signup(UserData user) {
        nameInput.shouldBe(visible).setValue(user.name());
        emailInput.setValue(user.email());
        signupButton.click();
        return new RegistrationPage();
    }

}
