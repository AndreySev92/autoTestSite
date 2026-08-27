package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupRequestDto {
    private String name;
    private String email;
}