package pp.ua.lomax.desk.dto.security;

import lombok.*;

import java.util.Set;

@Data
public class SignupRequestDTO {

    private String username;
    private String email;
    private String password;
    private Set<String> role;

}
