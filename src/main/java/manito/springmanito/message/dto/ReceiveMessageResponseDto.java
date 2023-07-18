package manito.springmanito.message.dto;

import lombok.Getter;
import manito.springmanito.message.entity.Message;

@Getter
public class ReceiveMessageResponseDto {
    private String contents;
    private String messageGiver;
    public ReceiveMessageResponseDto (Message message) {
        this.contents = message.getContent();
        this.messageGiver = message.getMessageGiver().getUserId();
    }
}
