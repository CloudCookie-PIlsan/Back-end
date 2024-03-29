package manito.springmanito.manito.service;

import jakarta.persistence.PrePersist;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

import static manito.springmanito.global.dto.ErorrMessage.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ManitoService {

    private final ManitoRepository manitoRepository;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final HttpServletRequest req;
    public static Date today = new Date();
    public static Date yesterday = new Date();


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
                new IllegalArgumentException(NOT_FOUND_USER)
        );

        Manito myManito = manitoRepository.findManitoByGiverNameAndToday(userId, today).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_TODAYMANITO)
        );

        return new TodayManitoResponseDto(myManito.getManitoReceiver().getUsername());
    }

    /**
     * 어제의 나를 마니또 한 사람 가져오기
     * @return
     */
    public YesterdayManitoResponseDto getYesterdayManito(String token) {
        String userId = getString(token);

        userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException(NOT_FOUND_USER)
        );

        Manito myManito = manitoRepository.findManitoByReceiverIdAndYesterday(userId, yesterday).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_YESTERDAYMANTITO)
        );

        return new YesterdayManitoResponseDto(myManito.getManitoSender().getUsername());
    }

    public AnswerResponseDto postAnswer(AnswerRequestDto answerRequestDto, String token) {
        String userId = getString(token);

        userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException(NOT_FOUND_USER)
        );


        Manito myManito = manitoRepository.findManitoByReceiverIdAndToday(userId, today).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_TODAYMANITO)
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


    @PrePersist
    public void prePersistToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 9);
        today = calendar.getTime();
    }

    @PrePersist
    public void prePersistYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -15);
        yesterday = calendar.getTime();
    }
}

