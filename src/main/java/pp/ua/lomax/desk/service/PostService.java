package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.EResponseMessage;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.post.PhotoDto;
import pp.ua.lomax.desk.dto.post.PostCreateDto;
import pp.ua.lomax.desk.dto.post.PostDeleteDto;
import pp.ua.lomax.desk.dto.post.PostPaginationDto;
import pp.ua.lomax.desk.dto.post.PostPutDto;
import pp.ua.lomax.desk.dto.post.PostResponseDto;
import pp.ua.lomax.desk.exeptions.BadDataRequestException;
import pp.ua.lomax.desk.exeptions.DataNotFoundException;
import pp.ua.lomax.desk.exeptions.EExceptionMessage;
import pp.ua.lomax.desk.exeptions.ForbiddenException;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.entity.PhotoEntity;
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.entity.RegionEntity;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.CategoryRepository;
import pp.ua.lomax.desk.persistance.repository.EPostStatus;
import pp.ua.lomax.desk.persistance.repository.PhotoRepository;
import pp.ua.lomax.desk.persistance.repository.PostRepository;
import pp.ua.lomax.desk.persistance.repository.RegionRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final RegionRepository regionRepository;
    private final PhotoRepository photoRepository;
    private final String uploadPhotoPath;
    private final String serverHost;
    private final String serverPort;

    public PostService(PostRepository postRepository,
                       CategoryRepository categoryRepository,
                       RegionRepository regionRepository,
                       PhotoRepository photoRepository,
                       @Value("${app.uploadPhotoPath}") String uploadPhotoPath,
                       @Value("${app.host}") String serverHost,
                       @Value("${server.port}") String serverPort) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.regionRepository = regionRepository;
        this.photoRepository = photoRepository;
        this.uploadPhotoPath = uploadPhotoPath;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    private PostResponseDto convertPostEntityToDto(PostEntity postEntity) {

        PostResponseDto postResponseDto = new PostResponseDto();

        postResponseDto.setId(postEntity.getId());
        postResponseDto.setCreated(postEntity.getCreated());
        postResponseDto.setUpdated(postEntity.getUpdated());
        postResponseDto.setName(postEntity.getName());
        postResponseDto.setDescription(postEntity.getDescription());
        postResponseDto.setAd(postEntity.getAd());
        postResponseDto.setStatus(postEntity.getStatus());
        postResponseDto.setVipExpDate(postEntity.getVipExpDate());
        postResponseDto.setCategory(postEntity.getCategory());
        postResponseDto.setRegion(postEntity.getRegion());
        postResponseDto.setUser(postEntity.getUser());
        postResponseDto.setPhoto(postEntity.getPhoto());

        return postResponseDto;
    }

    private PostEntity getPostEntityById(Long id) {
        return postRepository.findPostEntitiesById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(EExceptionMessage.POST_NOT_FOUND.getMessage()));
    }

    private CategoryEntity getCategoryEntityById(Long id) {
        if (id == null){
            throw new BadDataRequestException(EExceptionMessage.CATEGORY_CAN_NOT_BE_NULL.getMessage());
        }
        return categoryRepository.findCategoryEntityById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(EExceptionMessage.CATEGORY_NO_SUCH.getMessage()));
    }

    private RegionEntity getRegionEntityById(Long id){
        if(id == null){
            throw new BadDataRequestException(EExceptionMessage.REGION_CAN_NOT_BE_NULL.getMessage());
        }
        return regionRepository.findRegionEntityById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(EExceptionMessage.REGION_NOT_FOUND.getMessage()));
    }

    private PostPaginationDto convertPageToPostPaginationDto(Page<PostEntity> page, Integer pageNumber){
        Set<PostResponseDto> postResponseDtoSet = new HashSet<>();
        page.getContent().forEach(postEntity -> {
            PostResponseDto postResponseDto = convertPostEntityToDto(postEntity);
            postResponseDtoSet.add(postResponseDto);
        });

        PostPaginationDto postPaginationDto = new PostPaginationDto();
        postPaginationDto.setTotalElements(page.getTotalElements());
        postPaginationDto.setTotalPages(page.getTotalPages());
        postPaginationDto.setCurrentPage(pageNumber);
        postPaginationDto.setPostResponseDtoSet(postResponseDtoSet);

        return postPaginationDto;
    }

    public PostPaginationDto getPostsPaginationByCategory(Long categoryId,
                                                                   Integer pageNumber, Integer size) {

        CategoryEntity categoryEntity = getCategoryEntityById(categoryId);

        Pageable paging = PageRequest.of(pageNumber - 1, size, Sort.by("created").descending());

        Page<PostEntity> pageResult =
                postRepository.findByStatusAndCategory(EPostStatus.ACTIVE,
                        categoryEntity, paging);

        if (pageResult.getContent().isEmpty()) {
            throw new DataNotFoundException(EResponseMessage.CATEGORY_IS_EMPTY.getMessage());
        }

        return convertPageToPostPaginationDto(pageResult, pageNumber);
    }

    public PostPaginationDto getPostsPaginationByRegion(Long regionId,
                                                                   Integer pageNumber, Integer size) {

        RegionEntity regionEntity = getRegionEntityById(regionId);

        Pageable paging = PageRequest.of(pageNumber - 1, size, Sort.by("created").descending());

        Page<PostEntity> pageResult =
                postRepository.findByStatusAndRegion(EPostStatus.ACTIVE, regionEntity,
                         paging);

        if (pageResult.getContent().isEmpty()) {
            throw new DataNotFoundException(EResponseMessage.REGION_IS_EMPTY.getMessage());
        }

        return convertPageToPostPaginationDto(pageResult, pageNumber);
    }

    public PostPaginationDto getPostsPaginationByRegionAndCategory(Long regionId, Long categoryId,
                                                          Integer pageNumber, Integer size) {

        RegionEntity regionEntity =  getRegionEntityById(regionId);
        CategoryEntity categoryEntity = getCategoryEntityById(categoryId);

        Pageable paging = PageRequest.of(pageNumber - 1, size, Sort.by("created").descending());

        Page<PostEntity> pageResult =
                postRepository.findByStatusAndRegionAndCategory(EPostStatus.ACTIVE, regionEntity,
                                                                categoryEntity, paging);

        if (pageResult.getContent().isEmpty()) {
            throw new DataNotFoundException(EResponseMessage.CATEGORY_IS_EMPTY.getMessage());
        }

        return convertPageToPostPaginationDto(pageResult, pageNumber);
    }

    public PostResponseDto createPost(PostCreateDto postCreateDto,
                                      UserDetailsImpl userDetailsImpl) {


        CategoryEntity categoryEntity = getCategoryEntityById(postCreateDto.getCategory());

        RegionEntity regionEntity = getRegionEntityById(postCreateDto.getRegion());

        UserEntity user = userDetailsImpl.getUser();

        PostEntity postEntity = new PostEntity();
        postEntity.setName(postCreateDto.getName());
        postEntity.setDescription(postCreateDto.getDescription());
        postEntity.setAd(postCreateDto.getAd());
        postEntity.setPrice(postCreateDto.getPrice());
        postEntity.setStatus(EPostStatus.MODERATE);
        postEntity.setCategory(categoryEntity);
        postEntity.setRegion(regionEntity);
        postEntity.setUser(user);

        postRepository.save(postEntity);

        return convertPostEntityToDto(postEntity);

    }

    public PostResponseDto getPostById(Long id) {

        PostEntity postEntity = postRepository.findPostEntitiesById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(EExceptionMessage.POST_NOT_FOUND.getMessage()));

        return convertPostEntityToDto(postEntity);

    }

    public PostResponseDto putPost(PostPutDto postPutDto, UserDetailsImpl userDetailsImpl) {

        PostEntity postEntity = getPostEntityById(postPutDto.getId());

        if (userDetailsImpl.getId() != postEntity.getUser().getId()) {
            throw new ForbiddenException(EExceptionMessage.POST_PUT_ACCESS_IS_DENIED.getMessage());
        }

        CategoryEntity categoryEntity = getCategoryEntityById(postPutDto.getCategory());
        RegionEntity regionEntity = getRegionEntityById(postPutDto.getRegion());

        postEntity.setCategory(categoryEntity);
        postEntity.setRegion(regionEntity);
        postEntity.setName(postPutDto.getName());
        postEntity.setDescription(postPutDto.getDescription());
        postEntity.setAd(postPutDto.getAd());
        postEntity.setPrice(postPutDto.getPrice());
//        postEntity.setPhoto(postPutDto.getPhoto());

        postRepository.save(postEntity);
        log.info("Post put: ID: " + postEntity.getId() + "postName: " + postEntity.getName()
                + " user: " + userDetailsImpl.getUser().getUsername());

        return convertPostEntityToDto(postEntity);
    }

    public MessageResponseDto deletePost(PostDeleteDto postDeleteDto, UserDetailsImpl userDetailsImpl){

        PostEntity postEntity = getPostEntityById(postDeleteDto.getId());

        if (userDetailsImpl.getId() != postEntity.getUser().getId()){
            throw new ForbiddenException(EExceptionMessage.POST_DELETE_ACCESS_IS_DENIED.getMessage());
        }

        postRepository.delete(postEntity);
        log.info("Post delete: ID: " + postEntity.getId() + "postName: " + postEntity.getName()
                + " user: " + userDetailsImpl.getUser().getUsername());

        return new MessageResponseDto(EResponseMessage.POST_DELETED.getMessage());
    }

    public PostResponseDto uploadPhoto(PostPutDto postPutDto,
                                       MultipartFile uploadFile,
                                       UserDetailsImpl userDetailsImpl) {

        PostEntity postEntity = getPostEntityById(postPutDto.getId());

        if (userDetailsImpl.getId() != postEntity.getUser().getId()) {
            throw new ForbiddenException(EExceptionMessage.POST_PUT_ACCESS_IS_DENIED.getMessage());
        }

        CategoryEntity categoryEntity = getCategoryEntityById(postPutDto.getCategory());

        String uploadFileLink = createUploadFileLink(uploadFile, userDetailsImpl, categoryEntity);

        saveFile(uploadFileLink, uploadFile);

        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setLink(uploadFileLink);
        photoRepository.save(photoEntity);

        postEntity.getPhoto().add(photoEntity);

        postRepository.save(postEntity);

        return convertPostEntityToDto(postEntity);
    }

    public BufferedImage downloadPhoto(String fileName) {

        File file = new File(uploadPhotoPath + fileName);

        try (FileInputStream fis = new FileInputStream(file)) {
            BufferedImage bufferedImage = ImageIO.read(fis);
            return bufferedImage;
        } catch (FileNotFoundException e) {
            throw new DataNotFoundException(EExceptionMessage.FILE_NOT_FOUND.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public PostResponseDto removePhoto(PostPutDto postPutDto,
                                       PhotoDto photoDto,
                                       UserDetailsImpl userDetailsImpl) {

        PostEntity postEntity = getPostEntityById(postPutDto.getId());

        if (userDetailsImpl.getId() != postEntity.getUser().getId()) {
            throw new ForbiddenException(EExceptionMessage.POST_PUT_ACCESS_IS_DENIED.getMessage());
        }

        PhotoEntity photoEntity = photoRepository.getById(photoDto.getId());

        postEntity.getPhoto().remove(photoEntity);

        removeFile(photoEntity);

        postRepository.save(postEntity);

        return convertPostEntityToDto(postEntity);
    }

    private String createUploadFileLink(MultipartFile multipartFile,
                                        UserDetailsImpl userDetailsImpl,
                                        CategoryEntity categoryEntity) {

        String uploadFileName = multipartFile.getOriginalFilename();

        assert uploadFileName != null : EExceptionMessage.INVALID_FILE_NAME.getMessage();
        String fileSufix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);

        if (fileSufix.equals("jpg")) {
            fileSufix = "jpeg";
        } else if (!fileSufix.equals("png") && !fileSufix.equals("gif") && !fileSufix.equals("bmp")) {
            throw new BadDataRequestException(EExceptionMessage.UNSUPORTED_FILE_FORMAT.getMessage());
        }

        StringBuilder fileLink = new StringBuilder();
        fileLink.append(serverHost);
        fileLink.append(":");
        fileLink.append(serverPort);
        fileLink.append("/api/post/getphoto/");
        fileLink.append(String.valueOf(userDetailsImpl.getId()));
        fileLink.append("_");
        fileLink.append(String.valueOf(categoryEntity.getId()));
        fileLink.append("_");
        fileLink.append(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        fileLink.append(".");
        fileLink.append(fileSufix);

        return fileLink.toString();

    }

    private void saveFile(String fileLink,
                          MultipartFile uploadFile) {

        String fileName = fileLink.substring(fileLink.lastIndexOf("/") + 1);

        File photo = new File(uploadPhotoPath + fileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(photo)) {
            fileOutputStream.write(uploadFile.getBytes());
        } catch (FileNotFoundException e) {
            throw new DataNotFoundException(EExceptionMessage.FILE_NOT_FOUND.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeFile(PhotoEntity photoEntity) {
        String fileLink = photoEntity.getLink();
        String fileName = fileLink.substring(fileLink.lastIndexOf("/") + 1);
        File photo = new File(uploadPhotoPath + fileName);

        try {
            photo.delete();
        } catch (RuntimeException e) {
            new DataNotFoundException(EExceptionMessage.FILE_NOT_FOUND.getMessage());
        }
    }


}
