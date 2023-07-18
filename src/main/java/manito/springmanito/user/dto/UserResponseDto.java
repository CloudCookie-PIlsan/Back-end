package manito.springmanito.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String message;
    public UserResponseDto(String message) {
        this.message = message;
    }
}
