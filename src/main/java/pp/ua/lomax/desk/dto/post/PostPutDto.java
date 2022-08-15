package pp.ua.lomax.desk.dto.post;

import lombok.Data;
import pp.ua.lomax.desk.persistance.repository.EPostStatus;

@Data
public class PostPutDto {

    private Long id;
    private Long category;
    private Long region;
    private String name;
    private String description;
    private String ad;
    private Double price;
    private EPostStatus status;
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private Set<PhotoEntity> photo;

}
