package manito.springmanito.service;

import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.MessageRequestDto;
import manito.springmanito.dto.MessageResponseDto;
import manito.springmanito.manito.entity.Manito;
import manito.springmanito.entity.Message;
import manito.springmanito.entity.User;
import manito.springmanito.jwt.JwtUtil;
import manito.springmanito.manito.repository.ManitoRepository;
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
                () -> new IllegalArgumentException("당신은 쪽지를 보낼 수 있는 사용자가 아닙니다."));
        Manito myManito = manitoRepository.findManitoByGiverNameAndToday(loginUser.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("매칭된 마니또가 없습니다. 조금만 기다려주세요."));

        Message message = new Message(messageRequestDto, loginUser, myManito.getManitoReceiver());  // 받은 PostRequest -> Entity 로
        messageRepository.save(message);
        return new MessageResponseDto("쪽지가 발송되었습니다!");
    }
}
