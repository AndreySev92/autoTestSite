package testdata.builders;

import com.github.javafaker.Faker;
import dto.LoginRequestDto;
import dto.RegisterRequestDto;

public class TestDataGenerator {

    private static final Faker faker = new Faker();

    public static RegisterRequestDto generateRegisterRequest() {
        return RegisterRequestDto.builder()
                .name(faker.name().fullName())
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password(6, 20))
                .address1(faker.address().streetAddress())
                .country(faker.address().country())
                .state(faker.address().state())
                .city(faker.address().city())
                .zipcode(faker.address().zipCode())
                .mobile_number(faker.phoneNumber().phoneNumber())
                .build();
    }

    public static LoginRequestDto generateInvalidLoginRequest() {
        return LoginRequestDto.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
    }

    public static String uniqueEmail() {
        return faker.name().firstName().toLowerCase()
                + "_" + System.currentTimeMillis()
                + "@test.com";
    }

    public static String password() {
        return faker.internet().password(8, 16, true, true, true);
    }

    public static String name() {
        return faker.name().firstName();
    }

    public static String unknownEmail() {
        return faker.internet().emailAddress();
    }
}