package pp.ua.lomax.desk.dto.security;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String username;
    private String email;
    private String password;

}
