package testdata.builders;

import dto.LoginRequestDto;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class LoginWithInvalidEmailAndPassTest {

    public static Stream<Arguments> invalidLoginDataProvider() {
        return Stream.of(
                Arguments.of(
                        LoginRequestDto.builder()
                                .email("invalid@mail.com")
                                .password("wrongPass123")
                                .build()
                ),
                Arguments.of(
                        LoginRequestDto.builder()
                                .email("fake@example.com")
                                .password("123456")
                                .build()
                ),
                Arguments.of(
                        LoginRequestDto.builder()
                                .email("nonexistent@test.com")
                                .password("qwerty")
                                .build()
                ),
                Arguments.of(
                        LoginRequestDto.builder()
                                .email("test@test.com")
                                .password("password")
                                .build()
                )
        );
    }
}
