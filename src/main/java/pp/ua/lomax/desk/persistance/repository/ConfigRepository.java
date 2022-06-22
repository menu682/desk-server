package pp.ua.lomax.desk.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.ua.lomax.desk.persistance.entity.ConfigEntity;

public interface ConfigRepository extends JpaRepository<ConfigEntity, Long> {


}
