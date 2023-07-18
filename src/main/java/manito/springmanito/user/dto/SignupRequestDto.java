package manito.springmanito.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class SignupRequestDto {

    @NotNull
    private String username;

    @NotNull(message = "아이디는 필수 입력입니다.")
    @NotBlank(message = "아이디는 필수 입력입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}", message = "아이디는 4~10자의 영문 소문자와 숫자로만 입력해주세요.")
    private String userId;

    @NotNull(message = "비밀번호는 필수 입력입니다.")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 8~15자의 영문 대소문자, 숫자, 특수문자로 입력해주세요.")
    private String password;

}
