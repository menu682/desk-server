package pp.ua.lomax.desk.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import pp.ua.lomax.desk.persistance.entity.PhotoEntity;

import java.util.Set;

@Data
public class PostPutDto {

    private Long id;
    private Long category;
    private String name;
    private String description;
    private String ad;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<PhotoEntity> photo;

}
