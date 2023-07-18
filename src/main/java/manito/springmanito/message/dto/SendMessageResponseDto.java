package manito.springmanito.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import manito.springmanito.message.entity.Message;

import java.time.LocalDate;

@Getter
public class SendMessageResponseDto {
    private String contents;
    private String messageReceiver;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate sendDay;

    public SendMessageResponseDto(Message message) {
        this.contents = message.getContent();
        this.messageReceiver = message.getMessageReceiver().getUserId();
        this.sendDay = message.getCreatedAt();
    }
}
