package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginTestData {
    private String email;
    private String password;
    private int expectedStatusCode;
    private String expectedMessage;
    private TestResultType resultType;

    public enum TestResultType {
        SUCCESS,
        VALIDATION_ERROR
    }
}