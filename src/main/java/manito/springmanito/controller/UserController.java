package manito.springmanito.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.LoginRequestDto;
import manito.springmanito.dto.UserResponseDto;
import manito.springmanito.dto.SignupRequestDto;
import manito.springmanito.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public UserResponseDto signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }
    @PostMapping("/login")
    public UserResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        return userService.login(loginRequestDto, httpServletResponse);
    }
}
