package manito.springmanito.user.service;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import manito.springmanito.user.dto.LoginRequestDto;
import manito.springmanito.user.dto.LoginResponseDto;
import manito.springmanito.user.dto.LogoutResponseDto;
import manito.springmanito.user.dto.SignupRequestDto;
import manito.springmanito.user.entity.User;
import manito.springmanito.global.jwt.JwtUtil;
import manito.springmanito.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static manito.springmanito.global.dto.ErorrMessage.*;
import static manito.springmanito.global.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public LoginResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String userId = signupRequestDto.getUserId();
        String password = signupRequestDto.getPassword();

        String encodePassword = passwordEncoder.encode(password);

        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            throw new IllegalArgumentException(DUPLICATION_USER);
        }
        User savedUser = userRepository.save(new User(username, userId, encodePassword));

        return new LoginResponseDto(SIGNUP_USER);
    }

    // 로그인
    public LogoutResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        // user 찾기
        User findUser = userRepository.findByUserId(userId).orElseThrow(
                ()-> new IllegalArgumentException(NOT_FOUND_USER));

        // 비밀번호 일치여부 확인
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new IllegalArgumentException(NOT_MATCH_USERPASSWORD);
        }

        // 토큰 만들기
        String token = jwtUtil.createToken(findUser.getUserId());

        // 쿠키에 저장
//        jwtUtil.addJwtToCookie(token, httpServletResponse);

        return new LogoutResponseDto(token, LOGIN_USER);
    }
}