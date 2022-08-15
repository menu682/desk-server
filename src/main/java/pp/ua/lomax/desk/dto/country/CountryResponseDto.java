package pp.ua.lomax.desk.dto.country;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CountryResponseDto {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;

}
