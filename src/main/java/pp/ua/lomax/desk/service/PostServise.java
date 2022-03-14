package pp.ua.lomax.desk.service;

import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.post.CreatePostRequestDto;
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.PostRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class PostServise {

    private PostRepository postRepository;

    public PostServise(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public MessageResponseDto create(UserEntity user,
                                     CreatePostRequestDto createPostRequestDto) {

        PostEntity postEntity = new PostEntity();
        postEntity.setUser(user);
        //TODO сделать категории, вытащить из базы категорию по id и вставить сюда
        //postEntity.setCategory(createPostRequestDto.getCategoryId());
        postEntity.setName(createPostRequestDto.getPostName());
        postEntity.setDescription(createPostRequestDto.getDescription());
        postEntity.setAd(createPostRequestDto.getAd());
        postRepository.save(postEntity);
        return new MessageResponseDto("Объявление добавлено");
    }
}
