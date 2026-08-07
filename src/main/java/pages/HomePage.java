package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    // Локаторы
    private By signupLoginButton = By.cssSelector("a[href='/login']");
    private By loggedInUser = By.cssSelector("a[href='/logout']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://automationexercise.com/");
    }

    public void clickSignupLoginButton() {
        driver.findElement(signupLoginButton).click();
    }

    public boolean isUserLoggedIn() {
        return driver.findElements(loggedInUser).size() > 0;
    }
}