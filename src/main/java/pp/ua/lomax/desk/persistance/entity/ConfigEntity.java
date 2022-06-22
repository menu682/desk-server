package pp.ua.lomax.desk.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pp.ua.lomax.desk.persistance.repository.EConfigStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "config")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConfigEntity extends BaseEntity{

    @Column(name = "status")
    private EConfigStatus configStatus;

    @Column(name = "vip_day_cost")
    private Double vipDayCost;

}
