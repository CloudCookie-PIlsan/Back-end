package manito.springmanito.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MessageResponseDto {
    private String message;
    private int statusCode;

    public MessageResponseDto (String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
