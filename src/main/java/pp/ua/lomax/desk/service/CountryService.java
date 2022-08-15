package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.EResponseMessage;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.country.CountryAddDto;
import pp.ua.lomax.desk.dto.country.CountryDeleteDto;
import pp.ua.lomax.desk.dto.country.CountryPutDto;
import pp.ua.lomax.desk.dto.country.CountryResponseDto;
import pp.ua.lomax.desk.exeptions.EExceptionMessage;
import pp.ua.lomax.desk.exeptions.MessageRuntimeException;
import pp.ua.lomax.desk.persistance.entity.CountryEntity;
import pp.ua.lomax.desk.persistance.entity.RegionEntity;
import pp.ua.lomax.desk.persistance.repository.CountryRepository;
import pp.ua.lomax.desk.persistance.repository.RegionRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CountryService {

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;

    public CountryService(CountryRepository countryRepository,
                          RegionRepository regionRepository) {
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
    }

    public CountryResponseDto convertCountryEntityToDto(CountryEntity countryEntity){
        CountryResponseDto countryResponseDto = new CountryResponseDto();
        countryResponseDto.setId(countryEntity.getId());
        countryResponseDto.setName(countryEntity.getName());
        countryResponseDto.setCreated(countryEntity.getCreated());
        countryResponseDto.setUpdated(countryEntity.getUpdated());

        return countryResponseDto;
    }

    private CountryEntity findCountryByName(String name){
        Optional<CountryEntity> countryEntity = countryRepository.findCountryEntityByName(name);

        if(countryEntity.isEmpty()){
            throw new MessageRuntimeException(EExceptionMessage.COUNTRY_NOT_FOUND.getMessage());
        }

        return countryEntity.get();

    }

    private CountryEntity findCountryById(Long id){
        Optional<CountryEntity> countryEntity = countryRepository.findById(id);

        if(countryEntity.isEmpty()){
            throw new MessageRuntimeException(EExceptionMessage.COUNTRY_NOT_FOUND.getMessage());
        }

        return countryEntity.get();

    }

    public List<CountryResponseDto> getAllCountries(){
        List<CountryEntity> countryEntitiesList = countryRepository.findAll();
        List<CountryResponseDto> countryResponseDtoList = new ArrayList<>();
        countryEntitiesList.forEach(countryEntity -> {
            CountryResponseDto countryResponseDto = convertCountryEntityToDto(countryEntity);
            countryResponseDtoList.add(countryResponseDto);
        });

        return countryResponseDtoList;
    }

    public CountryResponseDto getCountryById(Long id){
        CountryEntity countryEntity = findCountryById(id);
        return convertCountryEntityToDto(countryEntity);
    }

    public CountryResponseDto addCountry(CountryAddDto countryAddDto, UserDetailsImpl userDetailsImpl){

        Optional<CountryEntity> countryEntity = countryRepository.findCountryEntityByName(countryAddDto.getName());
        if(countryEntity.isPresent()){
            throw new MessageRuntimeException(EExceptionMessage.COUNTRY_IS_EXISTS.getMessage());
        }

        CountryEntity saveCountryEntity = new CountryEntity();
        saveCountryEntity.setName(saveCountryEntity.getName());
        countryRepository.save(saveCountryEntity);

        CountryEntity newCountryEntity = findCountryByName(countryAddDto.getName());

        log.info("Add country: " + newCountryEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return convertCountryEntityToDto(newCountryEntity);

    }

    public CountryResponseDto putCountry(CountryPutDto countryPutDto, UserDetailsImpl userDetailsImpl){

        CountryEntity countryEntity = findCountryById(countryPutDto.getId());

        countryEntity.setName(countryPutDto.getName());
        countryRepository.save(countryEntity);

        log.info("Put country: " + countryEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return convertCountryEntityToDto(countryEntity);
    }

    public MessageResponseDto deleteCountry(CountryDeleteDto countryDeleteDto, UserDetailsImpl userDetailsImpl){

        Optional<List<RegionEntity>> regions = regionRepository.findRegionEntitiesByCountry(countryDeleteDto.getId());
        if(regions.isPresent()){
            throw new MessageRuntimeException(EExceptionMessage.COUNTRY_CONTAINS_REGIONS.getMessage());
        }

        CountryEntity countryEntity = findCountryById(countryDeleteDto.getId());
        countryRepository.delete(countryEntity);

        log.info("Delete country: " + countryEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return new MessageResponseDto(EResponseMessage.COUNTRY_DELETED.getMessage());

    }



}
