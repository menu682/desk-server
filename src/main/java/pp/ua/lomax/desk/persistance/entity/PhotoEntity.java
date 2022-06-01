package pp.ua.lomax.desk.persistance.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "photo")
@Data
public class PhotoEntity extends BaseEntity{

    @Column(name = "link", nullable = false)
    private String link;
}
