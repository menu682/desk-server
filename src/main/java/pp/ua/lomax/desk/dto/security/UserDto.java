package pp.ua.lomax.desk.dto.security;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String email;
    private String password;
// TODO убрать в отдаче поле пароля
}
