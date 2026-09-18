package testdata.builders;

public final class Users {
    private Users() {}

    public static final User VALID_USER = new User(
            "testuser@mail.ru",
            "123123"
    );

    public record User(String email, String password) {}
}
