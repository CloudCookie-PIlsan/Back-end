package manito.springmanito.manito.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YesterdayManitoResponseDto {

    private String manitoGiver;
    public YesterdayManitoResponseDto(String myManito) {
        this.manitoGiver = myManito;
    }
}

