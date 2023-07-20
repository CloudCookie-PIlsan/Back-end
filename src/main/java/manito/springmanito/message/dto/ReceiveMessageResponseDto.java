package manito.springmanito.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import manito.springmanito.message.entity.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class ReceiveMessageResponseDto {
    private String contents;
    private String sendPersonUsername;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date receiveDay;
    public ReceiveMessageResponseDto (Message message) {
        this.contents = message.getContent();
        this.sendPersonUsername = message.getMessageGiver().getUsername();
        this.receiveDay = message.getCreatedAt();
    }
}
