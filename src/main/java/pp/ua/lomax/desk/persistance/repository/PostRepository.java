package pp.ua.lomax.desk.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp.ua.lomax.desk.persistance.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {



}
