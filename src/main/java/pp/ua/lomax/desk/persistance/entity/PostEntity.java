package pp.ua.lomax.desk.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.EPostStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostEntity extends BaseEntity{


    @ManyToOne
    private CategoryEntity category;

    @ManyToOne
    private UserEntity user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private EPostStatus status;

    @Column(name = "foto")
    @OneToMany(fetch = FetchType.EAGER)
    Set<PhotoEntity> photo = new HashSet<>();


}

