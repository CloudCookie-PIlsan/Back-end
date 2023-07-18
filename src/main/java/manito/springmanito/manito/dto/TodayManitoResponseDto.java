package manito.springmanito.manito.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodayManitoResponseDto {

    private String manitoGiver;
    public TodayManitoResponseDto(String myManito) {
        this.manitoGiver = myManito;
    }
}
