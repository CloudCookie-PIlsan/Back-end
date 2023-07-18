package manito.springmanito.controller;

import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.MessageRequestDto;
import manito.springmanito.dto.MessageResponseDto;
import manito.springmanito.dto.UserResponseDto;
import manito.springmanito.service.MessageService;
import org.springframework.web.bind.annotation.*;

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
}
