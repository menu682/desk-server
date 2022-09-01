package pp.ua.lomax.desk.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPaginationDto {

    Long totalElements;
    Integer totalPages;
    Integer currentPage;
    Set<PostResponseDto> postResponseDtoSet;
}
