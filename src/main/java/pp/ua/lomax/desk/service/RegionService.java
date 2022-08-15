package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.EResponseMessage;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.region.RegionAddDto;
import pp.ua.lomax.desk.dto.region.RegionDeleteDto;
import pp.ua.lomax.desk.dto.region.RegionPutDto;
import pp.ua.lomax.desk.dto.region.RegionResponseDto;
import pp.ua.lomax.desk.exeptions.EExceptionMessage;
import pp.ua.lomax.desk.exeptions.MessageRuntimeException;
import pp.ua.lomax.desk.persistance.entity.CountryEntity;
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.entity.RegionEntity;
import pp.ua.lomax.desk.persistance.repository.PostRepository;
import pp.ua.lomax.desk.persistance.repository.RegionRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class RegionService {

    private final RegionRepository regionRepository;
    private final CountryService countryService;

    private final PostRepository postRepository;

    public RegionService(RegionRepository regionRepository,
                         CountryService countryService,
                         PostRepository postRepository) {
        this.regionRepository = regionRepository;
        this.countryService = countryService;
        this.postRepository = postRepository;
    }

    private RegionResponseDto convertRegionEntityToDto(RegionEntity regionEntity){
        RegionResponseDto regionResponseDto = new RegionResponseDto();
        regionResponseDto.setId(regionEntity.getId());
        regionResponseDto.setName(regionEntity.getName());
        regionResponseDto.setCountry(regionEntity.getCountry());
        regionResponseDto.setCreated(regionEntity.getCreated());
        regionResponseDto.setUpdated(regionEntity.getUpdated());

        return regionResponseDto;
    }

    private RegionEntity findRegionById(Long id){
        Optional<RegionEntity> regionEntity = regionRepository.findRegionById(id);

        if(regionEntity.isEmpty()){
            throw new MessageRuntimeException(EExceptionMessage.REGION_NOT_FOUND.getMessage());
        }

        return regionEntity.get();
    }

    private RegionEntity findRegionByName(String name, CountryEntity countryEntity){
        Optional<RegionEntity> regionEntity =
                regionRepository.findRegionEntityByNameAndCountry(name, countryEntity);

        if(regionEntity.isEmpty()){
            throw new MessageRuntimeException(EExceptionMessage.REGION_NOT_FOUND.getMessage());
        }

        return regionEntity.get();
    }

    public List<RegionResponseDto> getAllRegions(){

        List<RegionEntity> regionEntityList = regionRepository.findAll();
        List<RegionResponseDto> regionResponseDtoList = new ArrayList<>();

        regionEntityList.forEach(regionEntity -> {
            RegionResponseDto regionResponseDto = convertRegionEntityToDto(regionEntity);
            regionResponseDtoList.add(regionResponseDto);
        });

        return regionResponseDtoList;
    }

    public List<RegionResponseDto> getRegionsByCountry(Long countryId){

        CountryEntity countryEntity = countryService.findCountryById(countryId);

        List<RegionEntity> regionEntityList = regionRepository.findRegionEntityByCountry(countryEntity);
        List<RegionResponseDto> regionResponseDtoList = new ArrayList<>();

        regionEntityList.forEach(regionEntity -> {
            RegionResponseDto regionResponseDto = convertRegionEntityToDto(regionEntity);
            regionResponseDtoList.add(regionResponseDto);
        });

        return regionResponseDtoList;

    }

    public RegionResponseDto getRegionById(Long id){
        RegionEntity regionEntity = findRegionById(id);
        return convertRegionEntityToDto(regionEntity);
    }

    public RegionResponseDto addRegion(RegionAddDto regionAddDto, UserDetailsImpl userDetailsImpl){

        CountryEntity countryEntity = countryService.findCountryById(regionAddDto.getCountry());

        Optional<RegionEntity> checkRegionEntity =
                regionRepository.findRegionEntityByNameAndCountry(regionAddDto.getName(), countryEntity);

        if(checkRegionEntity.isPresent()){
            throw new MessageRuntimeException(EExceptionMessage.REGION_IS_EXISTS.getMessage());
        }

        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setName(regionAddDto.getName());
        regionEntity.setCountry(countryEntity);

        log.info("Add region: " + regionEntity.getName() +
                "in country" + countryEntity.getName() + "; " +
                "user: " + userDetailsImpl.getUsername());

        regionRepository.save(regionEntity);
        RegionEntity newRegion = findRegionByName(regionEntity.getName(), countryEntity);

        return convertRegionEntityToDto(newRegion);
    }

    public RegionResponseDto putRegion(RegionPutDto regionPutDto, UserDetailsImpl userDetailsImpl){

        RegionEntity regionEntity = findRegionById(regionPutDto.getId());

        CountryEntity countryEntity = countryService.findCountryById(regionPutDto.getCountry());

        regionEntity.setName(regionPutDto.getName());
        regionEntity.setCountry(countryEntity);

        regionRepository.save(regionEntity);

        log.info("Put region: " + regionEntity.getName() +
                "in country" + countryEntity.getName() + "; " +
                "user: " + userDetailsImpl.getUsername());

        return convertRegionEntityToDto(regionEntity);

    }

    public MessageResponseDto deleteRegion(RegionDeleteDto regionDeleteDto, UserDetailsImpl userDetailsImpl){

        RegionEntity regionEntity = findRegionById(regionDeleteDto.getId());

        List<PostEntity> posts = postRepository.findAllByRegion(regionEntity);
        if(!posts.isEmpty()){
            throw new MessageRuntimeException(EExceptionMessage.REGION_CONTAINS_POSTS.getMessage());
        }

        regionRepository.delete(regionEntity);

        log.info("Delete region: " + regionEntity.getName() + "; " + "user: " + userDetailsImpl.getUsername());

        return new MessageResponseDto(EResponseMessage.REGION_DELETED.getMessage());
    }

}
