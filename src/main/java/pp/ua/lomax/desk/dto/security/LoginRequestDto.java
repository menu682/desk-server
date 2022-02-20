package pp.ua.lomax.desk.dto.security;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String username;
    private String password;
}
