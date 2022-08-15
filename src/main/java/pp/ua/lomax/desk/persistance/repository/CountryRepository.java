package pp.ua.lomax.desk.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp.ua.lomax.desk.persistance.entity.CountryEntity;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Optional<CountryEntity> findCountryEntityByName(String name);

}
