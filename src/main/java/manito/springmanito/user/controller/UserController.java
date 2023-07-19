package manito.springmanito.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import manito.springmanito.user.dto.*;
import manito.springmanito.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public LoginResponseDto signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }
    @PostMapping("/login")
    public LogoutResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        return userService.login(loginRequestDto, httpServletResponse);
    }
}
