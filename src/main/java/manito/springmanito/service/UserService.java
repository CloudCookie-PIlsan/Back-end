package manito.springmanito.service;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.LoginRequestDto;
import manito.springmanito.dto.LoginResponseDto;
import manito.springmanito.dto.MessageResponseDto;
import manito.springmanito.dto.SignupRequestDto;
import manito.springmanito.entity.User;
import manito.springmanito.jwt.JwtUtil;
import manito.springmanito.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public MessageResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String userId = signupRequestDto.getUserId();
        String password = signupRequestDto.getPassword();

        String encodePassword = passwordEncoder.encode(password);

        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 있습니다.");
        }
        User savedUser = userRepository.save(new User(username, userId, encodePassword));

        return new MessageResponseDto("회원가입 성공", 200);
    }

    // 로그인
    public MessageResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        // user 찾기
        User findUser = userRepository.findByUserId(userId).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 비밀번호 일치여부 확인
        System.out.println("password = " + password);
        System.out.println(passwordEncoder.matches(password, findUser.getPassword()));
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            System.out.println("이프 탐");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        System.out.println("이프 안탐");
        // 토큰 만들기
        String token = jwtUtil.createToken(findUser.getUserId());

        // 쿠키에 저장
        jwtUtil.addJwtToCookie(token, httpServletResponse);

        return new MessageResponseDto("로그인이 되었습니다.", 200);
    }
}