package manito.springmanito.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import manito.springmanito.dto.ManitoResponseDto;
import manito.springmanito.entity.Manito;
import manito.springmanito.service.ManitoService;
import org.springframework.web.bind.annotation.*;

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
    public ManitoResponseDto hello() {
        return manitoService.getManito();
    }

}
