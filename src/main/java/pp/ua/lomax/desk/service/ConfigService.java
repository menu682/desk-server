package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.dto.config.ConfigDto;
import pp.ua.lomax.desk.exeptions.ConflictException;
import pp.ua.lomax.desk.exeptions.EExceptionMessage;
import pp.ua.lomax.desk.persistance.entity.ConfigEntity;
import pp.ua.lomax.desk.persistance.repository.ConfigRepository;
import pp.ua.lomax.desk.persistance.repository.EConfigStatus;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class ConfigService {

    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    /*
    * подразумевается что в таблице конфигурации должна быть только одна запись со статусом ACTIVE
    * которая используется в данный момент системой, остальные для сохранения истории и корректности
    * расчётов. Поэтому при изменениях конфигурации обязательно изменять статус текущей ACTIVE конфигурации
    * на CLOSE и создавать новую запись с ACTIVE
    */

    private ConfigEntity configDtoToEntity(ConfigDto configDto){
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setConfigStatus(configDto.getConfigStatus());
        configEntity.setVipDayCost(configDto.getVipDayCost());

        return configEntity;
    }

    public ConfigEntity getConfig(){
        return configRepository.findConfigEntityByConfigStatus(EConfigStatus.ACTIVE);
    }

    public ConfigDto saveConfig(ConfigDto configDto){

        ConfigEntity oldConfigEntity = getConfig();

        if(oldConfigEntity.getVipDayCost().equals(configDto.getVipDayCost())){
            throw new ConflictException(EExceptionMessage.CONFIG_NOT_CHANGE.getMessage());
        }

        oldConfigEntity.setConfigStatus(EConfigStatus.CLOSE);

        ConfigEntity newConfigEntity = configDtoToEntity(configDto);

        configRepository.save(oldConfigEntity);
        configRepository.save(newConfigEntity);

        log.info("Configuration update: new Vip Day Cost - " + newConfigEntity.getVipDayCost());

        ConfigDto newConfigDto = new ConfigDto();
        newConfigDto.setConfigStatus(newConfigEntity.getConfigStatus());
        newConfigDto.setVipDayCost(newConfigEntity.getVipDayCost());

        return newConfigDto;
    }

}
