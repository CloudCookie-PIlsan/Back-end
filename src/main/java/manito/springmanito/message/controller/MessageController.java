package manito.springmanito.message.controller;

import lombok.RequiredArgsConstructor;
import manito.springmanito.message.dto.MessageRequestDto;
import manito.springmanito.message.dto.MessageResponseDto;
import manito.springmanito.message.dto.ReceiveMessageResponseDto;
import manito.springmanito.message.dto.SendMessageResponseDto;
import manito.springmanito.message.service.MessageService;
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
            @RequestHeader("Authorization") String token
    ) {
        return messageService.sendMessage(messageRequestDto, token);
    }

    @GetMapping("/messages/send")
    public List<SendMessageResponseDto> getSendMessageBox (@RequestHeader(name = "Authorization", required = false) String token) {
        return messageService.getSendMessageBox(token);
    }

    @GetMapping("/messages/get")
    public List<ReceiveMessageResponseDto> geReceiveMessageBox (@RequestHeader(value = "Authorization", required = false) String token) {
        return messageService.getReceiveMessageBox(token);
    }

}
