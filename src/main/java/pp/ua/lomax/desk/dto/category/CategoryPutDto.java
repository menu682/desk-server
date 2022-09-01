package pp.ua.lomax.desk.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPutDto {

    private Long id;
    private String name;
    private Long parent;
}
