package manito.springmanito.manito.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import manito.springmanito.manito.dto.AnswerRequestDto;
import manito.springmanito.manito.dto.AnswerResponseDto;
import manito.springmanito.manito.dto.TodayManitoResponseDto;
import manito.springmanito.jwt.JwtUtil;
import manito.springmanito.manito.dto.YesterdayManitoResponseDto;
import manito.springmanito.manito.repository.ManitoRepository;
import manito.springmanito.repository.UserRepository;
import manito.springmanito.manito.entity.Manito;
import manito.springmanito.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
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
    public TodayManitoResponseDto getTodayManito() {
        Claims info = getClaims(req);
        // 아이디는 유니크
        String userId = info.getSubject();

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
    public YesterdayManitoResponseDto getYesterdayManito() {
        Claims info = getClaims(req);
        String userId = info.getSubject();

        userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 아이디가 없습니다.")
        );

        LocalDate yesterday = LocalDate.now().minusDays(1);
        Manito myManito = manitoRepository.findManitoByReceiverIdAndYesterday(userId, yesterday).orElseThrow(
                () -> new IllegalArgumentException("매칭된 마니또가 없었습니다.")
        );

        return new YesterdayManitoResponseDto(myManito.getManitoSender().getUsername());
    }

    public AnswerResponseDto postAnswer(AnswerRequestDto answerRequestDto) {
        Claims info = getClaims(req);
        String userId = info.getSubject();

        userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 아이디가 없습니다.")
        );

        Manito myManito = manitoRepository.findManitoByReceiverIdAndToday(userId).orElseThrow(
                () -> new IllegalArgumentException("매칭된 마니또가 없습니다.")
        );
        System.out.println(myManito.getManitoSender() + "현");;
        System.out.println(answerRequestDto.getUserName() + "req");
        if (myManito.isAnswered()) {
            return new AnswerResponseDto("이미 정답을 맞추셨습니다!");
        } else if (myManito.getManitoSender().getUsername().equals(answerRequestDto.getUserName())) {
            myManito.isAnswered(true);
            manitoRepository.save(myManito);
            return new AnswerResponseDto("정답입니다!");
        } else {
            return new AnswerResponseDto("오답입니다!");
        }
    }

    /**
     * 토큰 유효성 검증
     * @return
     */
    private Claims getClaims(HttpServletRequest req) {
        String tokenFromRequest = jwtUtil.getTokenFromRequest(req);
        tokenFromRequest = jwtUtil.substringToken(tokenFromRequest);
        // 토큰 검증
        if (!jwtUtil.validateToken(tokenFromRequest)) {
            throw new IllegalArgumentException("Token Error");
        }
        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(tokenFromRequest);
        return info;
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
        do {
            count++;
            Collections.shuffle(shuffledUsers);
            isValid = true;

            if (IntStream.range(0, users.size()).anyMatch(i -> users.get(i).equals(shuffledUsers.get(i)))) {
                isValid = false;
            }
        } while (!isValid);
        System.out.printf(count + "번째에 성공했습니다.");
        return shuffledUsers;
    }
}

