package manito.springmanito.message.dto;

import lombok.Getter;
import manito.springmanito.message.entity.Message;

@Getter
public class SendMessageResponseDto {
    private String contents;
    private String messageReceiver;

    public SendMessageResponseDto(Message message) {
        this.contents = message.getContent();
        this.messageReceiver = message.getMessageReceiver().getUserId();
    }
}
