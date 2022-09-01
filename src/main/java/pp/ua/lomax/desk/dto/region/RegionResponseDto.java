package pp.ua.lomax.desk.dto.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegionResponseDto {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private Long parent;

}
