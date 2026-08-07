package api;

import io.restassured.response.Response;

public class ProductServise extends BaseSpecification {
    private static final String PRODUCTS_LIST = "/productSList";
    private static final String BRANDS_LIST = "/brandsList";
    private static final String ADD_TO_CART = "/add_to_cart";
    private static final String VIEW_CART = "/view_cart";
    private static final String DELETE_CART = "/delete_cart";

    // Получить список продуктов
    public Response getProductsList(){
        return getBaseSpec()
                .when()
                .get(PRODUCTS_LIST)
                .then()
                .extract()
                .response();
    }

    public Response getBrandsList(){
        return getBaseSpec()
                .when()
                .get(BRANDS_LIST)
                .then()
                .extract()
                .response();
    }

    // Добавить продукт в корзину
    public Response addToCart(int productId) {
        return getBaseSpec()
                .queryParam("product_id", productId)
                .when()
                .post(ADD_TO_CART + "/" + productId)
                .then()
                .extract()
                .response();
    }

    // Посмотреть корзину
    public Response viewCart() {
        return getBaseSpec()
                .when()
                .get(VIEW_CART)
                .then()
                .extract()
                .response();
    }

    // Удалить продукт из корзины
    public Response deleteFromCart(int productId) {
        return getBaseSpec()
                .when()
                .delete(DELETE_CART + "/" + productId)
                .then()
                .extract()
                .response();
    }

}
