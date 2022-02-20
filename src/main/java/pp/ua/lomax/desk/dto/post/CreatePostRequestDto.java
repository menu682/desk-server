package pp.ua.lomax.desk.dto.post;

import lombok.Data;

@Data
public class CreatePostRequestDto {

    private String categoryId;
    private String postName;
    private String description;
    private String ad;

}
