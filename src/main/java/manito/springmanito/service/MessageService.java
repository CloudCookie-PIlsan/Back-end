package manito.springmanito.service;

import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.MessageRequestDto;
import manito.springmanito.dto.MessageResponseDto;
import manito.springmanito.entity.Message;
import manito.springmanito.entity.User;
import manito.springmanito.jwt.JwtUtil;
import manito.springmanito.repository.ManitoRepository;
import manito.springmanito.repository.MessageRepository;
import manito.springmanito.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ManitoRepository manitoRepository;
    private final JwtUtil jwtUtil;

    public MessageResponseDto sendMessage(MessageRequestDto messageRequestDto, String token) {
        jwtUtil.isTokenValid(token);
        String userId = jwtUtil.getUsernameFromToken(token);
        User loginUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("dd"));
        User myManito = manitoRepository.findMyManito(loginUser.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("매칭된 마니또가 없습니다. 조금만 기다려주세요."));

        Message message = new Message(messageRequestDto, loginUser, myManito);  // 받은 PostRequest -> Entity 로
        messageRepository.save(message);
        return new MessageResponseDto("쪽지가 발송되었습니다!");
    }


}
