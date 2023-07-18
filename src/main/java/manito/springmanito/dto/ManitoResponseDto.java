package manito.springmanito.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManitoResponseDto {

    private String manitoGiver;
    public ManitoResponseDto(String myManito) {
        this.manitoGiver = myManito;
    }
}
