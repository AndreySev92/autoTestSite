package ui.test;

import config.BaseUiSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.MainPage;
import testdata.builders.TestDataGenerator;
import ui.dto.UserData;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class AutomatSiteTest extends BaseUiSpec {

    @Test
    @DisplayName("UI-001-Регистрация нового пользователя")
    public void registrationTest() {
        // 1. Генерируем случайного пользователя через Faker
        UserData user = UserData.random();

        // 2. Проходим сценарий
        new MainPage()
                .open()          // открыть сайт
                .closeCheckCookies()           // закрыть cookie-баннер
                .goToSignupLogin()                 // шаг 1: Signup / Login
                .signup(user)                      // шаги 3–5: Name, Email, Signup
                .fillForm(user)                    // шаги 7–8: заполнить форму, Create
                .checkAccountCreated()             // проверка Account Created!
                .clickContinue()                   // вернуться на главную
                .deleteAccount()              // нажать "Delete Account"
                .checkAccountDeleted()        // "Account Deleted!"
                .clickContinue();             // вернуться на главную
    }

    @Test
    @DisplayName("UI-003-Добавление товара в корзину не авторизированный пользователь")
    public void addProductInCartTest() {

            // 1. Открываем главную
            MainPage mainPage = new MainPage()
                    .open()
                    .closeCheckCookies();

            // 2. Добавляем первый товар и проверяем модалку
            mainPage
                    .addProductToCart()
                    .checkAddedToCartModal();

            // 3. Переходим в корзину через "View Cart"
            CartPage cartPage = mainPage.viewCart();

            // 4. Проверяем, что товар в корзине
            cartPage.checkCartIsNotEmpty();
            assertThat(cartPage.getProductsCount()).isGreaterThan(0);
    }

    @Test
    @DisplayName("UI-004-Удаление товара из корзины не авторизированный пользователь")
    public void deleteProductFromCartTest() {
        // 1. Открываем главную
        MainPage mainPage = new MainPage()
                .open()
                .closeCheckCookies();

        // 2. Добавляем первый товар и проверяем модалку
        mainPage
                .addProductToCart()
                .checkAddedToCartModal();

        // 3. Переходим в корзину через "View Cart"
        CartPage cartPage = mainPage.viewCart();

        // 4. Проверяем, что товар в корзине
        cartPage.checkCartIsNotEmpty();
        assertThat(cartPage.getProductsCount()).isGreaterThan(0);

        //5. Удаляем товар из корзины
        cartPage.deleteProduct();
        cartPage.checkCartIsEmpty();
        assertThat(cartPage.getProductsCount()).isZero();
    }

    @Test
    @DisplayName("UI-005-Подписка на уведомления")
    public void subscribeTest() {
        String email = TestDataGenerator.uniqueEmail();

        new MainPage()
                .open()
                .closeCheckCookies()
                .emailToSubscribe(email)
                .clickSubscribe()
                .checkSubscribeSuccess();
    }

    @Test
    @DisplayName("UI-006- Выбор товара из категории")
    public void navigateToDressCategory() {
        new MainPage()
                .open()
                .closeCheckCookies()
                .clickWomenCategory()
                .clickDressSubcategory()
                .checkUrl()
                .checkTitle();
    }

}
