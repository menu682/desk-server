package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.EResponseMessage;
import pp.ua.lomax.desk.dto.post.PostCreateDto;
import pp.ua.lomax.desk.dto.post.PostPutDto;
import pp.ua.lomax.desk.dto.post.PostResponseDto;
import pp.ua.lomax.desk.exeptions.EExceptionMessage;
import pp.ua.lomax.desk.exeptions.MessageRuntimeException;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.CategoryRepository;
import pp.ua.lomax.desk.persistance.repository.PostRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class PostServise {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    public PostServise(PostRepository postRepository,
                       CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
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
    //TODO сделать получение постов с пагинецией

    //TODO 3. сделать загрузку фото
    //TODO 4. при загрузке фото менять название на userid_postid_timestamp, складывать фотки в статик/постимг, ссылку на фотку добавлять в БД
    //TODO 5. вызывать изменение поста при загрузке (считать из таблицы с фотками все фотки для данного поста и изменить пост)

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

        PostEntity postEntity = postRepository.findPostEntitiesById(postPutDto.getId())
                .orElseThrow(() ->
                        new MessageRuntimeException(EExceptionMessage.POST_NOT_FOUND.getMessage()));

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(postPutDto.getCategory())
                .orElseThrow(() ->
                        new MessageRuntimeException(EExceptionMessage.CATEGORY_NO_SUCH.getMessage()));

        postEntity.setCategory(categoryEntity);
        postEntity.setName(postPutDto.getName());
        postEntity.setDescription(postPutDto.getDescription());
        postEntity.setAd(postPutDto.getAd());
        postEntity.setPhoto(postPutDto.getPhoto());

        postRepository.save(postEntity);
        log.info("Post put: ID: " + postEntity.getId() + "postName: " + postEntity.getName()
                + " user: "+ userDetailsImpl.getUser().getUsername());

        return convertPostEntityToDto(postEntity);
    }
}
