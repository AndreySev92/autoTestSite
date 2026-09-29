package ui.test;

import config.BaseUiSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CartPage;
import pages.MainPage;
import testdata.builders.TestDataGenerator;
import ui.dto.UserData;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AutomatSiteTest extends BaseUiSpec {

    @Test
    @DisplayName("UI-001-Регистрация нового пользователя")
    public void registrationTest() {
        UserData user = UserData.random();

        new MainPage()
                .open()
                .closeCheckCookies()
                .goToSignupLogin()
                .signup(user)
                .fillForm(user)
                .checkAccountCreated()
                .clickContinue()
                .deleteAccount()
                .checkAccountDeleted()
                .clickContinue();
    }

    @Test
    @DisplayName("UI-003-Добавление товара в корзину ")
    public void addProductInCartTest() {

            MainPage mainPage = new MainPage()
                    .open()
                    .closeCheckCookies();

            mainPage
                    .addProductToCart()
                    .checkAddedToCartModal();

            CartPage cartPage = mainPage.viewCart();

            cartPage.checkCartIsNotEmpty();
            assertThat(cartPage.getProductsCount()).isGreaterThan(0);
    }

    @Test
    @DisplayName("UI-004-Удаление товара из корзины")
    public void deleteProductFromCartTest() {
        MainPage mainPage = new MainPage()
                .open()
                .closeCheckCookies();

        mainPage
                .addProductToCart()
                .checkAddedToCartModal();

        CartPage cartPage = mainPage.viewCart();

        cartPage.checkCartIsNotEmpty();
        assertThat(cartPage.getProductsCount()).isGreaterThan(0);

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
    public void navigateToDressCategoryTest() {
        new MainPage()
                .open()
                .closeCheckCookies()
                .clickWomenCategory()
                .clickDressSubcategory()
                .checkUrl()
                .checkTitle();
    }

    @Test
    @DisplayName("UI-007 Просмотр карточки товара Blue Top")
    public void openBlueTopCardTest() {
        new MainPage()
                .open()
                .closeCheckCookies()
                .clickProductCardBlueTop()
                .checkBlueTopPage();
    }

    @Test
    @DisplayName("UI-008- Выход из аккаунта")
    public void logoutTest() {
        UserData user = UserData.random();

        new MainPage()
                .open()
                .closeCheckCookies()
                .goToSignupLogin()
                .signup(user)
                .fillForm(user)
                .checkAccountCreated()
                .clickContinue()
                .checkUserLoggedIn()
                .clickLogout()
                .checkUserLoggedOut();
    }

    @ParameterizedTest(name = "UI-010 Логин с невалидными данными: email=''{0}'', password=''{1}''")
    @MethodSource("testdata.builders.InvalidLoginCases#cases")
    @DisplayName("UI-010 Логин с невалидными данными")
    public void loginWithInvalidCredentials(String email, String password) {
        new MainPage()
                .open()
                .closeCheckCookies()
                .goToLogin()
                .login(email,password)
                .checkLoginError();
    }

    @ParameterizedTest(name = "UI-011 Пустые поля: email=''{0}'', password=''{1}''")
    @MethodSource("testdata.builders.EmptyLoginCases#cases")
    @DisplayName("UI-011 Логин с пустыми полями")
    public void loginWithEmptyFields(String email, String password) {
        new MainPage()
                .open()
                .closeCheckCookies()
                .goToLogin()
                .login(email, password)
                .checkStayedOnLoginPage();
    }
}
