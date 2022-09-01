package pp.ua.lomax.desk.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp.ua.lomax.desk.persistance.entity.RegionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    Optional<RegionEntity> findRegionById(Long aLong);

    Optional<RegionEntity> findRegionEntityByNameAndParent(String name, Long parent);

    List<RegionEntity> findRegionEntityByParent(Long parent);

}
