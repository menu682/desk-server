package pp.ua.lomax.desk.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "photo")
public class PhotoEntity extends BaseEntity{

    @Column(name = "link", nullable = false)
    private String link;
}
