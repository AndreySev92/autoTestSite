package pages;

import com.codeborne.selenide.SelenideElement;
import ui.dto.UserData;

import static ExpectedMessages.ExpectedMessages.LOGIN_ERROR;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class LoginSignupPage {
    private final SelenideElement signupName = $("[data-qa='signup-name']");
    private final SelenideElement signupEmail = $("[data-qa='signup-email']");
    private final SelenideElement signupBtn = $("[data-qa='signup-button']");
    private final SelenideElement loginEmail = $("[data-qa='login-email']");
    private final SelenideElement loginPass = $("[data-qa='login-password']");
    private final SelenideElement loginBtn = $("[data-qa='login-button']");
    private final SelenideElement loginError = $("p:contains('Your email or password is incorrect!')");


    public RegistrationPage signup(UserData user) {
        signupName.shouldBe(visible).setValue(user.name());
        signupEmail.shouldBe(visible).setValue(user.email());
        signupBtn.shouldBe(visible).click();
        return new RegistrationPage();
    }

    public LoginSignupPage login(String email, String password) {
        loginEmail.setValue(email);
        loginPass.setValue(password);
        loginBtn.click();
        return this;
    }

    public LoginSignupPage checkLoginError() {
        loginError.shouldBe(visible).shouldHave(text(LOGIN_ERROR));
        return this;
    }


}
