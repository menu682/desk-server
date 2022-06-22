package pp.ua.lomax.desk.dto.config;

import lombok.Data;
import pp.ua.lomax.desk.persistance.repository.EConfigStatus;

import java.time.LocalDateTime;

@Data
public class ConfigDto {

    private LocalDateTime created;
    private LocalDateTime updated;
    private EConfigStatus configStatus;
    private Double vipDayCost;

}
