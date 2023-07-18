package manito.springmanito.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private String message;

    public UserResponseDto(String message) {
        this.message = message;
    }
}
