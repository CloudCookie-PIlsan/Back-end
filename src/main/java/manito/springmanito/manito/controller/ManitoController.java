package manito.springmanito.manito.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manito.springmanito.manito.dto.AnswerRequestDto;
import manito.springmanito.manito.dto.AnswerResponseDto;
import manito.springmanito.manito.dto.TodayManitoResponseDto;
import manito.springmanito.manito.dto.YesterdayManitoResponseDto;
import manito.springmanito.manito.service.ManitoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manitoes")
@Slf4j
public class ManitoController {

    private final ManitoService manitoService;

    /**
     * 매일 자정마다 마니또를 할당합니다.
     */
    @PostMapping("/test")
    public void assignManito() {
        manitoService.assignManito();
    }
    /**
     * 오늘의 마니또를 찾습니다.
     */
    @GetMapping("/giver")
    public TodayManitoResponseDto getTodayManito(@RequestHeader(name = "Authorization", required = false) String token) {
        log.info("토큰" + token);
        return manitoService.getTodayManito(token);
    }
    /**
     * 어제 나를 마니또 한 사람을 찾는다.
     */
    @GetMapping("/receiver")
    public YesterdayManitoResponseDto getYesterdayManito(@RequestHeader(name = "Authorization", required = false) String token) {
        log.info("토큰" + token);
        return manitoService.getYesterdayManito(token);
    }
    /**
     * 오늘 나를 마니또 해주는 사람을 맞춘다.
     */
    @PostMapping("/guessManito")
    public AnswerResponseDto postAnswer(@RequestBody AnswerRequestDto answerRequestDto, @RequestHeader(name = "Authorization", required = false) String token) {
        log.info("토큰" + token);
        return manitoService.postAnswer(answerRequestDto, token);
    }
}
