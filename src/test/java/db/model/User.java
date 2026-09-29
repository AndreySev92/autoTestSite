package db.model;

public record User(
        int id,
        String name,
        String email,
        String password
) {
    public static User of(String name, String email, String password) {
        return new User(0, name, email, password);
    }
}