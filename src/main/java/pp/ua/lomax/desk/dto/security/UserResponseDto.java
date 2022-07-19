package pp.ua.lomax.desk.dto.security;

import lombok.Data;
import pp.ua.lomax.desk.persistance.EUserStatus;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private EUserStatus status;
    private Double balance;

}
