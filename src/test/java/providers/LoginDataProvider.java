package providers;

import dto.LoginTestData;
import java.util.stream.Stream;

public class LoginDataProvider {

    public static Stream<LoginTestData> provideLoginData() {
        return Stream.of(
                // ✅ Успешные кейсы
                LoginTestData.builder()
                        .email("max@mail.ru")
                        .password("123123")
                        .expectedStatusCode(200)
                        .expectedMessage("User exists!")
                        .resultType(LoginTestData.TestResultType.SUCCESS)
                        .build(),

                // ❌ Валидационные ошибки
                LoginTestData.builder()
                        .email("wrong@mail.ru")
                        .password("wrongpass")
                        .expectedStatusCode(200)
                        .expectedMessage("User not found!")
                        .resultType(LoginTestData.TestResultType.VALIDATION_ERROR)
                        .build(),

                LoginTestData.builder()
                        .email("")
                        .password("123123")
                        .expectedStatusCode(200)
                        .expectedMessage("User not found!")
                        .resultType(LoginTestData.TestResultType.VALIDATION_ERROR)
                        .build(),

                LoginTestData.builder()
                        .email("max@mail.ru")
                        .password("")
                        .expectedStatusCode(200)
                        .expectedMessage("User not found!")
                        .resultType(LoginTestData.TestResultType.VALIDATION_ERROR)
                        .build()
        );
    }
}