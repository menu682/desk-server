package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.persistance.repository.ConfigRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class ConfigService {

    private ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    /*
    * подразумевается что в таблице конфигурации должна быть только одна запись со статусом ACTIVE
    * которая используется в данный момент системой, остальные для сохранения истории и корректности
    * расчётов. Поэтому при изменениях конфигурации обязательно изменять статус текущей ACTIVE конфигурации
    * на CLOSE и создавать новую запись с ACTIVE
    */

    //TODO создать внутренний метод getConfig для получения активной конфигурации
    //TODO создать внутренний метод-обёртку saveConfig для смены статуса активной конфигурации на закрытую и создания новой активной записи

    /*
    * !!! конфигурация меняется и сохраняется вцелом, ОДИН КОНФИГ ДТО
    *
    * getConfig
    * -> если в КОНФИГ ДТО данные отличаются от текущих
    * -> меняем конфигурацию (устанавливаем новые значения)
    * -> saveConfig
    *
    * по итогу получаем новую запись в таблице
    * */

    //TODO создать метод для установки конфигурации


}
