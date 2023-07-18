package manito.springmanito.controller;

import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.*;
import manito.springmanito.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/messages")
    public MessageResponseDto sendMessage(
            @RequestBody MessageRequestDto messageRequestDto,
            @CookieValue("Authorization") String token
    ) {
        return messageService.sendMessage(messageRequestDto, token);
    }

    @GetMapping("/messages/send")
    public List<SendMessageResponseDto> getSendMessageBox (@CookieValue("Authorization") String token) {
        return messageService.getSendMessageBox(token);
    }

    @GetMapping("/messages/get")
    public List<ReceiveMessageResponseDto> geReceiveMessageBox (@CookieValue("Authorization") String token) {
        return messageService.getReceiveMessageBox(token);
    }

}
