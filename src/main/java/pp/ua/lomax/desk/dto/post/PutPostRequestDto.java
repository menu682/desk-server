package pp.ua.lomax.desk.dto.post;

import lombok.Data;

@Data
public class PutPostRequestDto {

    private Long postId;
    private String categoryId;
    private String postName;
    private String description;
    private String ad;

}
