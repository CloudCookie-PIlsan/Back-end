package manito.springmanito.dto;

import lombok.Getter;
import manito.springmanito.entity.Message;
import manito.springmanito.entity.User;

@Getter
public class SendMessageResponseDto {
    private String contents;
    private String messageReceiver;

    public SendMessageResponseDto(Message message) {
        this.contents = message.getContent();
        this.messageReceiver = message.getMessageReceiver().getUserId();
    }
}
