package manito.springmanito.message.service;

import lombok.RequiredArgsConstructor;
import manito.springmanito.manito.entity.Manito;

import manito.springmanito.message.dto.MessageRequestDto;
import manito.springmanito.message.dto.MessageResponseDto;
import manito.springmanito.message.dto.ReceiveMessageResponseDto;
import manito.springmanito.message.dto.SendMessageResponseDto;
import manito.springmanito.message.entity.Message;
import manito.springmanito.message.repository.MessageRepository;
import manito.springmanito.user.entity.User;
import manito.springmanito.global.jwt.JwtUtil;
import manito.springmanito.manito.repository.ManitoRepository;
import manito.springmanito.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static manito.springmanito.global.dto.ErorrMessage.*;
import static manito.springmanito.global.dto.SuccessMessage.SEND_MESSAGE;

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
                () -> new IllegalArgumentException(NOT_FOUND_USER));


        Manito myManito = manitoRepository.findManitoByGiverNameAndToday(loginUser.getUserId()).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_TODAYMANITO));

        // 쪽지 보내기
        Message message = new Message(messageRequestDto, loginUser, myManito.getManitoReceiver());  // 받은 PostRequest -> Entity 로
        messageRepository.save(message);
        return new MessageResponseDto(SEND_MESSAGE);
    }


    // 보낸 쪽지함
    public List<SendMessageResponseDto> getSendMessageBox(String token) {
        jwtUtil.isTokenValid(token);
        // 로그인을 한 유저
        String userId = jwtUtil.getUsernameFromToken(token);
        User loginUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_USER));
        return messageRepository.findByMessageGiver(loginUser)
                .stream().limit(20).map(SendMessageResponseDto::new).toList();
    }

    // 받은 쪽지함
    public List<ReceiveMessageResponseDto> getReceiveMessageBox(String token) {
        jwtUtil.isTokenValid(token);
        // 로그인을 한 유저
        String userId = jwtUtil.getUsernameFromToken(token);
        User loginUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_USER));
        return  messageRepository.findByMessageReceiver(loginUser)
                .stream().limit(20).map(ReceiveMessageResponseDto::new).toList();
    }
}
