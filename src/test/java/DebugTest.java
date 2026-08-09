
import api.ProductServise;
import io.restassured.response.Response;

public class DebugTest {
    public static void main(String[] args) {
        ProductServise productService = new ProductServise();
        Response response = productService.getProductsList();

        System.out.println("=== СТАТУС ===");
        System.out.println(response.getStatusCode());
        System.out.println("=== ТЕЛО ОТВЕТА ===");
        System.out.println(response.asPrettyString());
    }
}