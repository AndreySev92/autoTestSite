package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CartPage {

    // Таблица товаров в корзине
    private final SelenideElement cartTable   = $("#cart_info_table");
    private final SelenideElement emptyCart   = $("#empty_cart");
    private final SelenideElement deleteButton = $("a.cart_quantity_delete");




    public CartPage checkCartIsNotEmpty() {
        cartTable.shouldBe(visible);
        return this;
    }

    public int getProductsCount() {
        return $$("#cart_info_table tbody tr").size();
    }

    public CartPage deleteProduct() {
        deleteButton.shouldBe(visible).click();
        return this;
    }

    public CartPage checkCartIsEmpty() {
        emptyCart.shouldBe(visible);
        return this;
    }

}