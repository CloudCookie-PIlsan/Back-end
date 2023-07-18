package manito.springmanito.service;

import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.MessageRequestDto;
import manito.springmanito.dto.MessageResponseDto;
import manito.springmanito.manito.entity.Manito;
import manito.springmanito.dto.ReceiveMessageResponseDto;
import manito.springmanito.dto.SendMessageResponseDto;

import manito.springmanito.entity.Message;
import manito.springmanito.entity.User;
import manito.springmanito.jwt.JwtUtil;
import manito.springmanito.manito.repository.ManitoRepository;
import manito.springmanito.repository.MessageRepository;
import manito.springmanito.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ManitoRepository manitoRepository;
    private final JwtUtil jwtUtil;

    // 쪽지 보내기
    public MessageResponseDto sendMessage(MessageRequestDto messageRequestDto, String token) {
        jwtUtil.isTokenValid(token);
        String userId = jwtUtil.getUsernameFromToken(token);
        // 로그인을 한 유저
        User loginUser = userRepository.findByUserId(userId).orElseThrow(

        Manito myManito = manitoRepository.findManitoByGiverNameAndToday(loginUser.getUserId()).orElseThrow(
        // 로그인 한 유저의 마니또 찾기
                () -> new IllegalArgumentException("매칭된 마니또가 없습니다. 조금만 기다려주세요."));

        // 쪽지 보내기
        Message message = new Message(messageRequestDto, loginUser, myManito.getManitoReceiver());  // 받은 PostRequest -> Entity 로
        messageRepository.save(message);
        return new MessageResponseDto("쪽지가 발송되었습니다!");
    }


    // 보낸 쪽지함
    public List<SendMessageResponseDto> getSendMessageBox(String token) {
        jwtUtil.isTokenValid(token);
        // 로그인을 한 유저
        String userId = jwtUtil.getUsernameFromToken(token);
        User loginUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("로그인을 해주세요!"));
        return messageRepository.findByMessageGiver(loginUser)
                .stream().map(SendMessageResponseDto::new).toList();
    }

    // 받은 쪽지함
    public List<ReceiveMessageResponseDto> getReceiveMessageBox(String token) {
        jwtUtil.isTokenValid(token);
        // 로그인을 한 유저
        String userId = jwtUtil.getUsernameFromToken(token);
        User loginUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("로그인을 해주세요!"));
        return  messageRepository.findByMessageReceiver(loginUser)
                .stream().map(ReceiveMessageResponseDto::new).toList();
    }
}
