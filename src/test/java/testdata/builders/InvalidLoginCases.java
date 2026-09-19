package testdata.builders;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static testdata.builders.Users.VALID_USER;

public final class InvalidLoginCases {

    private InvalidLoginCases() {}

    public static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("nonexistent@mail.com","bad123"),
                Arguments.of("nonexistent@mail.com",VALID_USER.password()),
                Arguments.of(VALID_USER.email(),"bad123")
        );
    }
}
