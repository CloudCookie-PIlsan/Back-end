package manito.springmanito.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import manito.springmanito.message.entity.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class SendMessageResponseDto {
    private String contents;
    private String getPersonUsername;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date sendDay;

    public SendMessageResponseDto(Message message) {
        this.contents = message.getContent();
        this.getPersonUsername = message.getMessageReceiver().getUsername();
        this.sendDay = message.getCreatedAt();
    }
}
