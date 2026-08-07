package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignupPage {
    WebDriver driver;
    WebDriverWait wait;

    // Локаторы для всех полей регистрации
    private By passwordInput = By.cssSelector("input[data-qa='password']");
    private By firstNameInput = By.cssSelector("input[data-qa='first_name']");
    private By lastNameInput = By.cssSelector("input[data-qa='last_name']");
    private By addressInput = By.cssSelector("input[data-qa='address']");
    private By countrySelect = By.cssSelector("select[data-qa='country']");
    private By stateInput = By.cssSelector("input[data-qa='state']");
    private By cityInput = By.cssSelector("input[data-qa='city']");
    private By zipcodeInput = By.cssSelector("input[data-qa='zipcode']");
    private By mobileInput = By.cssSelector("input[data-qa='mobile_number']");
    private By createAccountButton = By.cssSelector("button[data-qa='create-account']");
    private By successMessage = By.cssSelector("h2[data-qa='account-created']");

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillRegistrationForm(String password, String firstName, String lastName,
                                     String address, String country, String state,
                                     String city, String zipcode, String mobile) {
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(addressInput).sendKeys(address);

        // Для выпадающего списка (select)
        Select countrySelectElement = new Select(driver.findElement(countrySelect));
        countrySelectElement.selectByVisibleText(country);

        driver.findElement(stateInput).sendKeys(state);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(zipcodeInput).sendKeys(zipcode);
        driver.findElement(mobileInput).sendKeys(mobile);
    }

    public void clickCreateAccountButton() {
        driver.findElement(createAccountButton).click();
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return driver.findElement(successMessage).getText();
    }
}