package testdata.builders;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class SecurityTestData {


    public static final String SQL_INJECTION_DROP =
            "test@mail.com'); DROP TABLE users; --";

    public static final String SQL_INJECTION_OR =
            "' OR '1'='1";

    public static final String SQL_INJECTION_UNION =
            "test@mail.com' UNION SELECT * FROM users --";

    public static final String SQL_INJECTION_COMMENT =
            "test@mail.com'--";

    public static final String XSS_SCRIPT =
            "<script>alert('xss')</script>";

    public SecurityTestData() {}

    public static String tooLongName(int length) {
        return "a".repeat(length);
    }

    public static Stream<Arguments> sqlInjectionEmails() {
        return Stream.of(
                Arguments.of(SQL_INJECTION_DROP),
                Arguments.of(SQL_INJECTION_OR),
                Arguments.of(SQL_INJECTION_UNION),
                Arguments.of(SQL_INJECTION_COMMENT),
                Arguments.of(XSS_SCRIPT)
        );
    }
}
