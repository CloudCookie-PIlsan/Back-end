package manito.springmanito.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String message;

    public LoginResponseDto (String message) {
        this.message = message;
    }
}
