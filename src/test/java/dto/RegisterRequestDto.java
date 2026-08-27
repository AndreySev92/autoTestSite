package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequestDto {
    private String name;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String address1;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String mobile_number;
}