package manito.springmanito.dto;

import lombok.Getter;
import manito.springmanito.entity.Message;
import manito.springmanito.entity.User;

@Getter
public class ReceiveMessageResponseDto {
    private String contents;
    private String messageGiver;
    public ReceiveMessageResponseDto (Message message) {
        this.contents = message.getContent();
        this.messageGiver = message.getMessageGiver().getUserId();
    }
}
