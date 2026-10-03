package testdata.builders;

import db.model.User;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class InvalidUserData {

    public static User userWithNullName(){
        return User.of(
                null,
                TestDataGenerator.uniqueEmail(),
                TestDataGenerator.password()
        );
    }

    public static User userWithNullEmail(){
        return User.of(
                TestDataGenerator.name(),
                null,
                TestDataGenerator.password()
        );
    }

    public static  User userWithNullPassword(){
        return User.of(
                TestDataGenerator.name(),
                TestDataGenerator.uniqueEmail(),
                null
        );
    }

    public static Stream<Arguments> userWithNullNameAndEmail(){
        return Stream.of(
                Arguments.of(userWithNullName()),
                Arguments.of(userWithNullEmail()),
                Arguments.of(userWithNullPassword())

        );
    }
    public static User userWithLongName() {
        return User.of(
                TestDataGenerator.randomNameLength(101),
                TestDataGenerator.uniqueEmail(),
                TestDataGenerator.password()
        );
    }

    public static User userWithLongEmail() {
        String longEmail = "a".repeat(250) + "@mail.com";   // email VARCHAR(255) → >255 не влезет
        return User.of(
                TestDataGenerator.name(),
                longEmail,
                TestDataGenerator.password()
        );
    }

    public static User userWithLongPassword() {
        String longPassword = "a".repeat(256);   // password VARCHAR(255) → 256 не влезет
        return User.of(
                TestDataGenerator.name(),
                TestDataGenerator.uniqueEmail(),
                longPassword
        );
    }

    public static Stream<Arguments> tooLongFieldCases() {
        return Stream.of(
                Arguments.of("name > 100",     userWithLongName()),
                Arguments.of("email > 255",    userWithLongEmail()),
                Arguments.of("password > 255", userWithLongPassword())
        );
    }


}
