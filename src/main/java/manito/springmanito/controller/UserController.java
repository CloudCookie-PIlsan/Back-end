package manito.springmanito.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.LoginRequestDto;
import manito.springmanito.dto.MessageResponseDto;
import manito.springmanito.dto.SignupRequestDto;
import manito.springmanito.service.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public MessageResponseDto signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }
    @PostMapping("/login")
    public MessageResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        return userService.login(loginRequestDto, httpServletResponse);
    }
}
