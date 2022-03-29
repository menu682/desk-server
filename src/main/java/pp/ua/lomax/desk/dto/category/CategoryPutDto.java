package pp.ua.lomax.desk.dto.category;

import lombok.Data;

@Data
public class CategoryPutDto {

    private Long id;
    private String name;
    private Long parent;
}
