package pp.ua.lomax.desk.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostEntity extends BaseEntity{


    @Column(name = "category", nullable = false)
    @ManyToOne
    private CategoryEntity category;

    @Column(name = "user", nullable = false)
    @ManyToOne
    private UserEntity user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "ad", nullable = false)
    private String ad;

//    @Column(name = "foto")
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(	name = "photo", joinColumns = @JoinColumn(name = "id"))
//    Set<PhotoEntity> photos = new HashSet<>();


}

