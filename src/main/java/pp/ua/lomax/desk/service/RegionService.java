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
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.entity.RegionEntity;
import pp.ua.lomax.desk.persistance.repository.PostRepository;
import pp.ua.lomax.desk.persistance.repository.RegionRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class RegionService {

    private final RegionRepository regionRepository;
    private final PostRepository postRepository;

    public RegionService(RegionRepository regionRepository,
                         PostRepository postRepository) {
        this.regionRepository = regionRepository;
        this.postRepository = postRepository;
    }

    private RegionResponseDto convertRegionEntityToDto(RegionEntity regionEntity){
        RegionResponseDto regionResponseDto = new RegionResponseDto();
        regionResponseDto.setId(regionEntity.getId());
        regionResponseDto.setCreated(regionEntity.getCreated());
        regionResponseDto.setUpdated(regionEntity.getUpdated());
        regionResponseDto.setName(regionEntity.getName());
        regionResponseDto.setParent(regionEntity.getParent());
        return regionResponseDto;
    }

    public List<RegionResponseDto> getAllRegions(){
        List<RegionEntity> regionEntityList = regionRepository.findAll();

        return regionEntityList.stream().map(this::convertRegionEntityToDto).toList();
    }

    public RegionResponseDto getRegionById(Long id){

        RegionEntity regionEntity = regionRepository.findRegionById(id)
                .orElseThrow(() ->
                    new MessageRuntimeException(EExceptionMessage.REGION_NOT_FOUND.getMessage())
                );

        return convertRegionEntityToDto(regionEntity);
    }

    public List<RegionResponseDto> getRegionsByParent(Long parent){
        List<RegionEntity> regionEntityList = regionRepository.findRegionEntityByParent(parent);

        if(regionEntityList.isEmpty()){
            throw new MessageRuntimeException(EExceptionMessage.REGION_NOT_FOUND.getMessage());
        }

        return regionEntityList.stream().map(this::convertRegionEntityToDto).toList();

    }

    public RegionResponseDto addRegion(RegionAddDto regionAddDto, UserDetailsImpl userDetailsImpl){

        Optional<RegionEntity> findRegionEntity = regionRepository.findRegionEntityByNameAndParent(
                regionAddDto.getName(), regionAddDto.getParent());

        if(findRegionEntity.isPresent()){
            throw new MessageRuntimeException(EExceptionMessage.REGION_IS_EXISTS.getMessage());
        }

        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setName(regionAddDto.getName());
        regionEntity.setParent(regionAddDto.getParent());

        RegionEntity saveRegionEntity = regionRepository.save(regionEntity);

        log.info("Add region: " + saveRegionEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return convertRegionEntityToDto(saveRegionEntity);

    }

    public RegionResponseDto putRegion(RegionPutDto regionPutDto, UserDetailsImpl userDetailsImpl){

        RegionEntity regionEntity = regionRepository.findById(regionPutDto.getId())
                .orElseThrow(() ->
                        new MessageRuntimeException(EExceptionMessage.REGION_NOT_FOUND.getMessage()));

        regionEntity.setName(regionPutDto.getName());
        regionEntity.setParent(regionPutDto.getParent());

        RegionEntity saveRegionEntity = regionRepository.save(regionEntity);

        log.info("Put region: " + saveRegionEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return convertRegionEntityToDto(saveRegionEntity);

    }

    public MessageResponseDto deleteRegion(RegionDeleteDto regionDeleteDto, UserDetailsImpl userDetailsImpl){
        List<RegionEntity> regionEntityList =
                regionRepository.findRegionEntityByParent(regionDeleteDto.getId());

        if(!regionEntityList.isEmpty()){
            throw new MessageRuntimeException(EExceptionMessage.REGION_IS_PARENT.getMessage());
        }

        RegionEntity regionEntity = regionRepository.findById(regionDeleteDto.getId())
                .orElseThrow(() ->
                        new MessageRuntimeException(EExceptionMessage.REGION_NOT_FOUND.getMessage()));

        List<PostEntity> postEntityList = postRepository.findAllByRegion(regionEntity);

        if(!postEntityList.isEmpty()){
            throw new MessageRuntimeException(EExceptionMessage.REGION_CONTAINS_POSTS.getMessage());
        }

        regionRepository.delete(regionEntity);

        log.info("Delete region: " + regionEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return new MessageResponseDto(EResponseMessage.REGION_DELETED.getMessage());
    }

}
