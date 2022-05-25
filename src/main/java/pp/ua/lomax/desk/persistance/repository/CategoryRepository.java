package pp.ua.lomax.desk.persistance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository <CategoryEntity, Long> {


    @Override
    @Query(value = "from CategoryEntity")
    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findCategoryEntityById(Long categoryId);

    List<Optional<CategoryEntity>> findCategoryEntityByParent(Long parent);

    Optional<CategoryEntity> findCategoryEntityByNameAndParent(String name, Long parent);

}
