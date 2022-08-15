package pp.ua.lomax.desk.dto.region;

import lombok.Data;
import pp.ua.lomax.desk.persistance.entity.CountryEntity;

import java.time.LocalDateTime;

@Data
public class RegionResponseDto {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private CountryEntity country;

}
