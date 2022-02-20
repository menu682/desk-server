package pp.ua.lomax.desk.persistance.entity.security;

import pp.ua.lomax.desk.persistance.ERole;
import pp.ua.lomax.desk.persistance.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ERole name;

    public RoleEntity() {
    }

    public RoleEntity(Long id,
                      LocalDateTime created,
                      LocalDateTime updated,
                      ERole name) {
        super(id, created, updated);
        this.name = name;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
