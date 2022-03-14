package pp.ua.lomax.desk.dto.category;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponseDto {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private Long parent;

}
