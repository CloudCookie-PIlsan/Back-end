package manito.springmanito.manito.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YesterdayManitoResponseDto {
    /**
     * 어제의 마니또 이름
     */
    private String manitoReceiver;
    public YesterdayManitoResponseDto(String myManito) {
        this.manitoReceiver = myManito;
    }
}

