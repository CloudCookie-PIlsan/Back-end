package manito.springmanito.user.service;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import manito.springmanito.user.dto.LoginRequestDto;
import manito.springmanito.user.dto.UserResponseDto;
import manito.springmanito.user.dto.SignupRequestDto;
import manito.springmanito.user.entity.User;
import manito.springmanito.global.jwt.JwtUtil;
import manito.springmanito.user.repository.UserRepository;
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
    public UserResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String userId = signupRequestDto.getUserId();
        String password = signupRequestDto.getPassword();

        String encodePassword = passwordEncoder.encode(password);

        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 있습니다.");
        }
        User savedUser = userRepository.save(new User(username, userId, encodePassword));

        return new UserResponseDto("회원가입 성공");
    }

    // 로그인
    public UserResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        // user 찾기
        User findUser = userRepository.findByUserId(userId).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 비밀번호 일치여부 확인
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰 만들기
        String token = jwtUtil.createToken(findUser.getUserId());

        // 쿠키에 저장
        jwtUtil.addJwtToCookie(token, httpServletResponse);

        return new UserResponseDto("로그인이 되었습니다.");
    }
}