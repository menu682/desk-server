package pp.ua.lomax.desk.dto.security;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String username;
    private String password;
}
