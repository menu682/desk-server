package pp.ua.lomax.desk.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.entity.PhotoEntity;
import pp.ua.lomax.desk.persistance.entity.RegionEntity;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.EPostStatus;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private String description;
    private String ad;
    private Double price;
    private CategoryEntity category;
    private RegionEntity region;
    private UserEntity user;
    private EPostStatus status;
    private LocalDateTime vipExpDate;
    private Set<PhotoEntity> photo;


}
