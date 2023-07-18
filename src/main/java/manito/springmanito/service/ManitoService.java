package manito.springmanito.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.ManitoResponseDto;
import manito.springmanito.jwt.JwtUtil;
import manito.springmanito.repository.ManitoRepository;
import manito.springmanito.repository.UserRepository;
import manito.springmanito.entity.Manito;
import manito.springmanito.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ManitoService {

    private final ManitoRepository manitoRepository;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final HttpServletRequest req;

    public void assignManito() {
        List<User> users = userRepository.findAll();
        manitoRepository.deleteAll();
        List<User> shuffledUsers = new ArrayList<>(users);
        Collections.shuffle(shuffledUsers);

        IntStream.range(0, users.size())
                .forEach(i -> {
                    User sender = users.get(i);
                    User receiver = shuffledUsers.get((i + 1) % users.size());

                    Manito manito = new Manito(sender, receiver);

                    manitoRepository.save(manito);
                });
    }

    public ManitoResponseDto getManito() {
        Claims info = getClaims(req);
        String userId = info.getSubject();
        userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 아이디가 없습니다.")
        );

        User myManito = manitoRepository.findMyManito(userId).orElseThrow(
                () -> new IllegalArgumentException("매칭된 마니또가 없습니다. 조금만 기다려주세요."));
        return new ManitoResponseDto(myManito.getUsername());
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
}

