package pp.ua.lomax.desk.persistance.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp.ua.lomax.desk.persistance.ERole;
import pp.ua.lomax.desk.persistance.entity.security.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(ERole name);

}
