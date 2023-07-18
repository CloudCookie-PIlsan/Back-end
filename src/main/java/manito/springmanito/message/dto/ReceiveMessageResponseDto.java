package manito.springmanito.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import manito.springmanito.message.entity.Message;

import java.time.LocalDate;

@Getter
public class ReceiveMessageResponseDto {
    private String contents;
    private String messageGiver;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate receiveDay;
    public ReceiveMessageResponseDto (Message message) {
        this.contents = message.getContent();
        this.messageGiver = message.getMessageGiver().getUserId();
        this.receiveDay = message.getCreatedAt();
    }
}
