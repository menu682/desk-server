package pp.ua.lomax.desk.dto.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegionPutDto {

    private Long id;
    private String name;
    private Long parent;
}
