package pp.ua.lomax.desk.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pp.ua.lomax.desk.persistance.entity.PhotoEntity;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDto {

    private Long category;
    private Long region;
    private String name;
    private String description;
    private String ad;
    private Double price;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<PhotoEntity> photo;

}
