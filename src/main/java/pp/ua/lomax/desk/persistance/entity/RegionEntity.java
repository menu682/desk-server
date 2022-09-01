package pp.ua.lomax.desk.persistance.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "region")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegionEntity extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent", nullable = false)
    private Long parent;

}
