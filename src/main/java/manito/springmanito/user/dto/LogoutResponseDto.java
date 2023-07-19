package manito.springmanito.user.dto;

import lombok.Getter;

@Getter
public class LogoutResponseDto {
    private String token;
    private String message;

    public LogoutResponseDto (String token, String message) {
        this.token = token;
        this.message = message;
    }
}
