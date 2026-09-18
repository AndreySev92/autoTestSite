package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final SelenideElement searchInput      = $("#search_product");
    private final SelenideElement searchButton     = $("#submit_search");
    private final SelenideElement settingsButton   = $("button.fc-cta-manage-options");
    private final SelenideElement acceptAllBtn     = $x("//p[contains(@class,'fc-button-label') and contains(text(),'Принять все')]");
    private final SelenideElement signupLoginBtn   = $("a[href='/login']");
    private final SelenideElement deleteAccountBtn = $("a[href='/delete_account']");    // Появляется после успешной регистрации/авторизации
    private final SelenideElement addToCartBtn     = $("a.add-to-cart");
    private final SelenideElement viewCartLink     = $("#cartModal a[href='/view_cart']");
    private final SelenideElement addedModal       = $("#cartModal");
    private final SelenideElement addedModalTitle  = $("#cartModal .modal-title");
    private final SelenideElement subscribeEmailInput = $("#susbscribe_email");
    private final SelenideElement subscribeButton = $("#subscribe");
    private final SelenideElement subscribeSuccessMsg  = $("#success-subscribe");
    private final SelenideElement womenCategory  = $x("//a[@href='#Women']");
    private final SelenideElement subCategoryDress  = $x("//a[@href='/category_products/1']");
    private final SelenideElement viewProductCardBlueTop  = $x("//a[@href='/product_details/1']");
    private final SelenideElement logoutBtn  = $x("//a[@href='/logout']");




    public MainPage open() {
        Selenide.open(Configuration.baseUrl);
        return this;
    }

    public MainPage closeCheckCookies(){
        if (settingsButton.is(visible)) {
            settingsButton.click();
            acceptAllBtn.click();
        }
        return this;
    }

    public MainPage search(String query) {
        searchInput.shouldBe(visible).setValue(query);
        searchButton.click();
        return this;
    }

    public SignupPage goToSignupLogin() {
        signupLoginBtn.shouldBe(visible).click();
        return new SignupPage();
    }

    public AccountDeletedPage deleteAccount() {
        deleteAccountBtn.shouldBe(visible).click();
        return new AccountDeletedPage();
    }

    public MainPage addProductToCart() {
        addToCartBtn.shouldBe(visible).click();
        return this;
    }

    public MainPage checkAddedToCartModal() {
        addedModal.shouldBe(visible);
        addedModalTitle.shouldHave(text("Added!"));
        return this;
    }

    public CartPage viewCart() {
        viewCartLink.shouldBe(visible).click();
        return new CartPage();
    }

    public MainPage clickSubscribe (){
        subscribeButton.shouldBe(visible).click();
        return this;
    }

    public MainPage emailToSubscribe(String email) {
        subscribeEmailInput.shouldBe(visible).setValue(email);
        return this;
    }

    public MainPage checkSubscribeSuccess() {
        subscribeSuccessMsg
                .shouldBe(visible)
                .shouldHave(Condition.exactText("You have been successfully subscribed!"));
        return this;
    }

    public MainPage clickWomenCategory() {
        womenCategory.shouldBe(visible).click();
        return this;
    }

    public CategoryProductDressPage clickDressSubcategory() {
        subCategoryDress.shouldBe(visible).click();
        return new CategoryProductDressPage();
    }

    public BlueTopCardPage clickProductCardBlueTop() {
        viewProductCardBlueTop.shouldBe(visible).click();
        return new BlueTopCardPage();
    }

    public MainPage clickLogout() {
        logoutBtn.shouldBe(visible).click();
        return this;
    }

    public MainPage checkUserLoggedOut() {
        signupLoginBtn.shouldBe(visible);
        logoutBtn.shouldNotBe(visible);
        return this;
    }

    public MainPage checkUserLoggedIn() {
        logoutBtn.shouldBe(visible);
        return this;
    }


}
