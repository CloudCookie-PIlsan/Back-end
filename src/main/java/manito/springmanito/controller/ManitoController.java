package manito.springmanito.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.ManitoResponseDto;
import manito.springmanito.entity.Manito;
import manito.springmanito.service.ManitoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manitoes")
public class ManitoController {

    private final ManitoService manitoService;

    @PostMapping("/test")
    public void assignManito() {
        manitoService.assignManito();
    }

    @GetMapping("/giver")
    public ManitoResponseDto getManito() {
        return manitoService.getManito();
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "hello2";
    }

    @GetMapping("/hello3")
    public String hello3() {
        return "hello2";
    }
}
