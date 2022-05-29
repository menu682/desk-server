package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.post.PostCreateDto;
import pp.ua.lomax.desk.dto.post.PostPutDto;
import pp.ua.lomax.desk.dto.post.PostResponseDto;
import pp.ua.lomax.desk.exeptions.EExceptionMessage;
import pp.ua.lomax.desk.exeptions.MessageRuntimeException;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.entity.PhotoEntity;
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.CategoryRepository;
import pp.ua.lomax.desk.persistance.repository.PhotoRepository;
import pp.ua.lomax.desk.persistance.repository.PostRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class PostServise {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private PhotoRepository photoRepository;

    private String uploadPath;
    private String serverHost;
    private String serverPort;

    public PostServise(PostRepository postRepository,
                       CategoryRepository categoryRepository,
                       PhotoRepository photoRepository,
                       @Value("${app.uploadPath}") String uploadPath,
                       @Value("${app.host}") String serverHost,
                       @Value("${server.port}") String serverPort) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
        this.uploadPath = uploadPath;
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
        postResponseDto.setCategory(postEntity.getCategory());
        postResponseDto.setUser(postEntity.getUser());
        postResponseDto.setPhoto(postEntity.getPhoto());

        return postResponseDto;
    }

    private PostEntity getPostEntityById(Long id){
        return postRepository.findPostEntitiesById(id)
                .orElseThrow(() ->
                        new MessageRuntimeException(EExceptionMessage.POST_NOT_FOUND.getMessage()));
    }

    private CategoryEntity getCategoryEntityById(Long id){
        return categoryRepository.findCategoryEntityById(id)
                .orElseThrow(() ->
                        new MessageRuntimeException(EExceptionMessage.CATEGORY_NO_SUCH.getMessage()));
    }

    //TODO сделать получение постов с пагинецией

    public PostResponseDto createPost(PostCreateDto postCreateDto,
                                      UserDetailsImpl userDetailsImpl) {

        CategoryEntity categoryEntity =
                categoryRepository.findCategoryEntityById(postCreateDto.getCategory())
                        .orElseThrow(() ->
                                new MessageRuntimeException(EExceptionMessage.CATEGORY_NO_SUCH.getMessage()));

        UserEntity user = userDetailsImpl.getUser();

        PostEntity postEntity = new PostEntity();
        postEntity.setName(postCreateDto.getName());
        postEntity.setDescription(postCreateDto.getDescription());
        postEntity.setAd(postCreateDto.getAd());
        postEntity.setCategory(categoryEntity);
        postEntity.setUser(user);

        postRepository.save(postEntity);

        return convertPostEntityToDto(postEntity);

    }

    public PostResponseDto getPostById(Long id) {

        PostEntity postEntity = postRepository.findPostEntitiesById(id)
                .orElseThrow(() ->
                        new MessageRuntimeException(EExceptionMessage.POST_NOT_FOUND.getMessage()));

        return convertPostEntityToDto(postEntity);

    }

    public PostResponseDto putPost(PostPutDto postPutDto, UserDetailsImpl userDetailsImpl) {

        PostEntity postEntity = getPostEntityById(postPutDto.getId());

        if(userDetailsImpl.getId() != postEntity.getUser().getId()){
            throw new MessageRuntimeException(EExceptionMessage.POST_PUT_ACCESS_IS_DENIED.getMessage());
        }

        CategoryEntity categoryEntity = getCategoryEntityById(postPutDto.getCategory());

        postEntity.setCategory(categoryEntity);
        postEntity.setName(postPutDto.getName());
        postEntity.setDescription(postPutDto.getDescription());
        postEntity.setAd(postPutDto.getAd());
        postEntity.setPhoto(postPutDto.getPhoto());

        postRepository.save(postEntity);
        log.info("Post put: ID: " + postEntity.getId() + "postName: " + postEntity.getName()
                + " user: " + userDetailsImpl.getUser().getUsername());

        return convertPostEntityToDto(postEntity);
    }

    public PostResponseDto uploadPhoto(PostPutDto postPutDto,
                                      MultipartFile uploadFile,
                                       UserDetailsImpl userDetailsImpl){

        PostEntity postEntity = getPostEntityById(postPutDto.getId());

        if(userDetailsImpl.getId() != postEntity.getUser().getId()){
            throw new MessageRuntimeException(EExceptionMessage.POST_PUT_ACCESS_IS_DENIED.getMessage());
        }

        CategoryEntity categoryEntity = getCategoryEntityById(postPutDto.getCategory());

        String uploadFileName = uploadFile.getOriginalFilename();

        StringBuffer fileName = new StringBuffer();
        fileName.append(String.valueOf(userDetailsImpl.getId()));
        fileName.append("_");
        fileName.append(String.valueOf(categoryEntity.getId()));
        fileName.append("_");
        fileName.append(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        fileName.append(".");
        fileName.append(uploadFileName.substring(uploadFileName.lastIndexOf(".")));

        File photo = new File(uploadPath + fileName.toString());

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(photo);
            fileOutputStream.write(uploadFile.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setLink(serverHost + ":" + serverPort + "/photo/" + photo.getName());
        photoRepository.save(photoEntity);

        Set<PhotoEntity> photoEntityList = postEntity.getPhoto();
        photoEntityList.add(photoEntity);
        postEntity.setPhoto(photoEntityList);
        postRepository.save(postEntity);

        return convertPostEntityToDto(postEntity);
    }

}
