package testdata.builders;

import dto.LoginRequestDto;
import dto.RegisterRequestDto;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class LoginWithoutEmailDataProvider {

    public static Stream<Arguments> loginWithoutEmailDataProvider() {
        RegisterRequestDto registerRequest = TestDataGenerator.generateRegisterRequest();

        return Stream.of(
                Arguments.of(
                        LoginRequestDto.builder()
                                .email(null)
                                .password(registerRequest.getPassword())
                                .build()
                ),
                Arguments.of(
                        LoginRequestDto.builder()
                                .email(registerRequest.getEmail())
                                .password(null)
                                .build()
                ),
                Arguments.of(
                        LoginRequestDto.builder()
                                .email(null)
                                .password(null)
                                .build()
                )
        );
    }
}
