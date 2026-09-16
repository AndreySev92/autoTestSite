package ui.dto;

import com.github.javafaker.Faker;

public record UserData(
        String name,
        String email,
        String password,
        String firstName,
        String lastName,
        String company,
        String address1,
        String address2,
        String country,
        String state,
        String city,
        String zipcode,
        String phone,
        String day,
        String month,
        String year
) {
    private static final Faker FAKER = new Faker();

    public static UserData random() {
        return new UserData(
                FAKER.name().firstName(),
                FAKER.internet().emailAddress(),
                FAKER.internet().password(8, 16, true, true, true),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.company().name(),
                FAKER.address().streetAddress(),
                FAKER.address().secondaryAddress(),
                "United States", // фиксированное значение — сайт принимает только из списка
                FAKER.address().state(),
                FAKER.address().city(),
                FAKER.address().zipCode(),
                FAKER.phoneNumber().cellPhone().replaceAll("[^0-9]", ""),
                String.valueOf(FAKER.number().numberBetween(1, 28)),
                String.valueOf(FAKER.number().numberBetween(1, 12)),
                String.valueOf(FAKER.number().numberBetween(1970, 2000))
        );
    }
}