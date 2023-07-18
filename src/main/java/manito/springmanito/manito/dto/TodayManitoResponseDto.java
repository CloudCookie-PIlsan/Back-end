package manito.springmanito.manito.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodayManitoResponseDto {
    /**
     * 오늘의 마니또 이름
     */
    private String manitoGiver;
    public TodayManitoResponseDto(String myManito) {
        this.manitoGiver = myManito;
    }
}
