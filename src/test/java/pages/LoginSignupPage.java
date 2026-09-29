package pages;

import com.codeborne.selenide.SelenideElement;
import ui.dto.UserData;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static testdata.builders.ExpectedMessages.LOGIN_ERROR;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;


public class LoginSignupPage {
    private final SelenideElement signupName = $("[data-qa='signup-name']");
    private final SelenideElement signupEmail = $("[data-qa='signup-email']");
    private final SelenideElement signupBtn = $("[data-qa='signup-button']");
    private final SelenideElement loginEmail = $("[data-qa='login-email']");
    private final SelenideElement loginPass = $("[data-qa='login-password']");
    private final SelenideElement loginBtn = $("[data-qa='login-button']");
    private final SelenideElement loginError = $x("//p[text()='" + LOGIN_ERROR + "']");
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

    public LoginSignupPage checkStayedOnLoginPage() {
        webdriver().shouldHave(urlContaining("/login"));
        return this;
    }
}
