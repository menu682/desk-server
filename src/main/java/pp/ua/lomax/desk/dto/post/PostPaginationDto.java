package pp.ua.lomax.desk.dto.post;

import lombok.Data;

import java.util.Set;

@Data
public class PostPaginationDto {

    Long totalElements;
    Integer totalPages;
    Integer currentPage;
    Set<PostResponseDto> postResponseDtoSet;
}
