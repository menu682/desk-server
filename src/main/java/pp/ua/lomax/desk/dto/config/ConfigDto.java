package pp.ua.lomax.desk.dto.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pp.ua.lomax.desk.persistance.repository.EConfigStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDto {

    private LocalDateTime created;
    private LocalDateTime updated;
    private EConfigStatus configStatus;
    private Double vipDayCost;

}
