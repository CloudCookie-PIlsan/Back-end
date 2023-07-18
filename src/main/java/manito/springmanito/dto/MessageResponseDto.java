package manito.springmanito.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MessageResponseDto {
    private String message;

    public MessageResponseDto (String message) {
        this.message = message;
    }
}
