package manito.springmanito.manito.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponseDto {
    /**
     * 유저 이름
     */
    private String username = "";
    /**
     * 성공 여부
     */
    private boolean success;

    /**
     * 생성자
     * @param success 성공 여부
     * @param username 유저 이름
     */
    public AnswerResponseDto(boolean success, String username) {
        this.username = username;
        this.success = success;
    }
    /**
     * 생성자
     * @param success 성공 여부
     */
    public AnswerResponseDto(boolean success) {
        this.success = success;
    }
}
