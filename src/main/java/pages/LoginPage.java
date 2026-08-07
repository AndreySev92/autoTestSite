package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // Локаторы для формы регистрации (Signup)
    private By nameInput = By.cssSelector("input[data-qa='signup-name']");
    private By emailInput = By.cssSelector("input[data-qa='signup-email']");
    private By signupButton = By.cssSelector("button[data-qa='signup-button']");

    // Локаторы для формы входа (Login)
    private By loginEmailInput = By.cssSelector("input[data-qa='login-email']");
    private By loginPasswordInput = By.cssSelector("input[data-qa='login-password']");
    private By loginButton = By.cssSelector("button[data-qa='login-button']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Методы для регистрации
    public void enterName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void clickSignupButton() {
        driver.findElement(signupButton).click();
    }

    // Методы для входа
    public void enterLoginEmail(String email) {
        driver.findElement(loginEmailInput).sendKeys(email);
    }

    public void enterLoginPassword(String password) {
        driver.findElement(loginPasswordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}