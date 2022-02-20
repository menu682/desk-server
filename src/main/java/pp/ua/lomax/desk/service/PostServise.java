package pp.ua.lomax.desk.service;

import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.post.CreatePostRequestDto;
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;

@Service
public class PostServise {

    private PostEntity postEntity;

    public PostServise() {
    }

    public PostServise(PostEntity postEntity) {
        this.postEntity = postEntity;
    }

    public MessageResponseDto create(UserEntity user,
                                     CreatePostRequestDto createPostRequestDto) {

        return null;
    }
}
