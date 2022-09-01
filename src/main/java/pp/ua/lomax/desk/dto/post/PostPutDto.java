package pp.ua.lomax.desk.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pp.ua.lomax.desk.persistance.repository.EPostStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
