package pp.ua.lomax.desk.service;

import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.persistance.repository.PostRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class PostServise {

    private PostRepository postRepository;

    public PostServise(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


}
