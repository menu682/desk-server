package pp.ua.lomax.desk.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.entity.RegionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findPostEntitiesById(Long id);

    List<PostEntity> findByCategory(CategoryEntity categoryEntity);

    List<PostEntity> findAllByRegion(RegionEntity regionEntity);

    //@Query(value = "from PostEntity as post where post.category = ?2")
    Page<PostEntity> findByStatusAndRegionAndCategory(EPostStatus status,
                             RegionEntity regionEntity,
                             CategoryEntity categoryEntity,
                             Pageable pageable);

    Page<PostEntity> findByStatusAndRegion(EPostStatus status,
                                                      RegionEntity regionEntity,
                                                      Pageable pageable);

    Page<PostEntity> findByStatusAndCategory(EPostStatus status,
                                                      CategoryEntity categoryEntity,
                                                      Pageable pageable);
}
