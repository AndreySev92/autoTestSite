package testdata.builders;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static testdata.builders.Users.VALID_USER;

public final class EmptyLoginCases {
    private EmptyLoginCases() {}

    public static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("",""),
                Arguments.of(VALID_USER.email(),""),
                Arguments.of("",VALID_USER.password())
        );
    }
}