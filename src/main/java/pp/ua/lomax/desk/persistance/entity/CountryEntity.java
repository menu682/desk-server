package pp.ua.lomax.desk.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "country")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryEntity extends BaseEntity{

    @Column(name = "name", nullable = false, unique = true)
    String name;

}
