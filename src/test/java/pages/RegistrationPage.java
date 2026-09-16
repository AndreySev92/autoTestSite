package pages;

import com.codeborne.selenide.SelenideElement;
import ui.dto.UserData;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class RegistrationPage {
    private final SelenideElement genderMr     = $("#id_gender1");
    private final SelenideElement password     = $("#password");
    private final SelenideElement day          = $("#days");
    private final SelenideElement month        = $("#months");
    private final SelenideElement year         = $("#years");
    private final SelenideElement newsletter   = $("#newsletter");
    private final SelenideElement optin        = $("#optin");
    private final SelenideElement firstName    = $("#first_name");
    private final SelenideElement lastName     = $("#last_name");
    private final SelenideElement company      = $("#company");
    private final SelenideElement address1     = $("#address1");
    private final SelenideElement address2     = $("#address2");
    private final SelenideElement country      = $("#country");
    private final SelenideElement state        = $("#state");
    private final SelenideElement city         = $("#city");
    private final SelenideElement zipcode      = $("#zipcode");
    private final SelenideElement mobile       = $("#mobile_number");
    private final SelenideElement createButton = $("[data-qa='create-account']");

    public AccountCreatedPage fillForm(UserData user) {
        genderMr.shouldBe(visible).click();
        password.setValue(user.password());

        day.selectOptionByValue(user.day());
        month.selectOptionByValue(user.month());
        year.selectOptionByValue(user.year());

        newsletter.click();
        optin.click();

        firstName.setValue(user.firstName());
        lastName.setValue(user.lastName());
        company.setValue(user.company());
        address1.setValue(user.address1());
        address2.setValue(user.address2());
        country.selectOption(user.country());
        state.setValue(user.state());
        city.setValue(user.city());
        zipcode.setValue(user.zipcode());
        mobile.setValue(user.phone());

        createButton.click();
        return new AccountCreatedPage();
    }

}
