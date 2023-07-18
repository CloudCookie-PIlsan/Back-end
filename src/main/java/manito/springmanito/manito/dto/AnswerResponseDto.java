package manito.springmanito.manito.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponseDto {

    private String message;

    public AnswerResponseDto(String message) {
        this.message = message;
    }
}
