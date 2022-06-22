package pp.ua.lomax.desk.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.entity.PostEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findPostEntitiesById(Long id);

    List<Optional<PostEntity>> findByCategory(CategoryEntity categoryEntity);

    //@Query(value = "from PostEntity as post where post.category = ?2")
    Page<PostEntity> findByStatusAndCategory(EPostStatus status,
                             CategoryEntity categoryEntity,
                             Pageable pageable);


}
