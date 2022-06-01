package pp.ua.lomax.desk.dto.post;

import lombok.Data;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.entity.PhotoEntity;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.EPostStatus;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostResponseDto {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private String description;
    private String ad;
    private CategoryEntity category;
    private UserEntity user;
    private EPostStatus status;
    private Set<PhotoEntity> photo;


}
