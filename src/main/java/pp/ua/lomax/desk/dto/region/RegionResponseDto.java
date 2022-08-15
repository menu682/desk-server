package pp.ua.lomax.desk.dto.region;

import lombok.Data;
import pp.ua.lomax.desk.persistance.entity.CountryEntity;

@Data
public class RegionResponseDto {

    private Long id;
    private String name;
    private CountryEntity country;

}
