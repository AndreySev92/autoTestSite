package testdata;

import com.github.javafaker.Faker;
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



}