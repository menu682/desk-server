package pp.ua.lomax.desk.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.ua.lomax.desk.persistance.entity.PhotoEntity;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
}
