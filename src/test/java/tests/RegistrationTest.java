package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HomePage;
import pages.LoginPage;
import pages.SignupPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {

    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    SignupPage signupPage;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    void testSuccessfulUserRegistration() {
        String uniqueEmail = "test.user+" + System.currentTimeMillis() + "@example.com";
        String name = "TestUser";
        String password = "Password123!";

        // Шаг 1: Открыть главную страницу
        homePage.open();

        // Шаг 2: Нажать Signup / Login
        homePage.clickSignupLoginButton();

        // Шаг 3-5: Заполнить имя и email, нажать Signup
        loginPage.enterName(name);
        loginPage.enterEmail(uniqueEmail);
        loginPage.clickSignupButton();

        // Шаг 6: Заполнить все поля на странице регистрации
        signupPage.fillRegistrationForm(
                password,          // Пароль
                "John",            // Имя
                "Doe",             // Фамилия
                "123 Main St",     // Адрес
                "Canada",          // Страна
                "Ontario",         // Регион/штат
                "Toronto",         // Город
                "M5V 2H1",         // Почтовый индекс
                "1234567890"       // Номер телефона
        );

        // Шаг 7: Нажать Create Account
        signupPage.clickCreateAccountButton();

        // Проверка: сообщение об успешной регистрации
        String successMessage = signupPage.getSuccessMessage();
        assertTrue(successMessage.contains("ACCOUNT CREATED!"),
                "Сообщение об успешной регистрации не найдено!");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}