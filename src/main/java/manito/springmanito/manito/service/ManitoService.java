package manito.springmanito.manito.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import manito.springmanito.manito.dto.AnswerRequestDto;
import manito.springmanito.manito.dto.AnswerResponseDto;
import manito.springmanito.manito.dto.TodayManitoResponseDto;
import manito.springmanito.global.jwt.JwtUtil;
import manito.springmanito.manito.dto.YesterdayManitoResponseDto;
import manito.springmanito.manito.repository.ManitoRepository;
import manito.springmanito.user.repository.UserRepository;
import manito.springmanito.manito.entity.Manito;
import manito.springmanito.user.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Log4j2
public class ManitoService {

    private final ManitoRepository manitoRepository;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final HttpServletRequest req;

    /**
     * 오늘의 마니또 매칭
     */
    public void assignManito() {
        List<User> users = userRepository.findAll();

        List<User> shuffledUsers = findValidManitoAssignment(users);

        for (int i = 0; i < users.size(); i++) {
            User sender = users.get(i);
            User receiver = shuffledUsers.get(i);

            Manito manito = new Manito(sender, receiver);
            manitoRepository.save(manito);
        }
    }

    /**
     * 오늘의 마니또 가져오기
     * @return
     */
    public TodayManitoResponseDto getTodayManito(String token) {
        String userId = getString(token);

        userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 아이디가 없습니다.")
        );

        Manito myManito = manitoRepository.findManitoByGiverNameAndToday(userId).orElseThrow(
                () -> new IllegalArgumentException("매칭된 마니또가 없습니다. 조금만 기다려주세요.")
        );

        return new TodayManitoResponseDto(myManito.getManitoSender().getUsername());
    }

    /**
     * 어제의 나를 마니또 한 사람 가져오기
     * @return
     */
    public YesterdayManitoResponseDto getYesterdayManito(String token) {
        String userId = getString(token);

        userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 아이디가 없습니다.")
        );

        LocalDate yesterday = LocalDate.now().minusDays(1);
        Manito myManito = manitoRepository.findManitoByReceiverIdAndYesterday(userId, yesterday).orElseThrow(
                () -> new IllegalArgumentException("매칭된 마니또가 없었습니다.")
        );

        return new YesterdayManitoResponseDto(myManito.getManitoSender().getUsername());
    }

    public AnswerResponseDto postAnswer(AnswerRequestDto answerRequestDto, String token) {
        String userId = getString(token);

        userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 아이디가 없습니다.")
        );

        Manito myManito = manitoRepository.findManitoByReceiverIdAndToday(userId).orElseThrow(
                () -> new IllegalArgumentException("매칭된 마니또가 없습니다.")
        );

        if (myManito.isAnswered()) {
            return new AnswerResponseDto(true, myManito.getManitoSender().getUsername());
        } else if (myManito.getManitoSender().getUsername().equals(answerRequestDto.getUsername())) {
            myManito.isAnswered(true);
            manitoRepository.save(myManito);
            return new AnswerResponseDto(true, myManito.getManitoSender().getUsername());
        } else {
            return new AnswerResponseDto(false);
        }
    }

    private String getString(String token) {
        jwtUtil.isTokenValid(token);
        return jwtUtil.getUsernameFromToken(token);
    }
    /**
     * 유효한 매칭 찾기
     * @param users
     * @return
     */
    private List<User> findValidManitoAssignment(List<User> users) {
        List<User> shuffledUsers = new ArrayList<>(users);
        boolean isValid;
        int count = 0;
        Collections.shuffle(shuffledUsers);
        do {
            count++;
            Collections.shuffle(shuffledUsers);
            isValid = true;

            if (IntStream.range(0, users.size()).anyMatch(i -> users.get(i).equals(shuffledUsers.get(i)))) {
                isValid = false;
            }
        } while (!isValid);
        return shuffledUsers;
    }
}

