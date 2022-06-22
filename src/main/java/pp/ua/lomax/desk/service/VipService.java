package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.persistance.repository.PostRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class VipService {

    PostRepository postRepository;

    public VipService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //TODO сделать метод addBalance(Double balance, UserDetailsImpl user) для пополнения баланса юзера
    //TODO сделать метод changeBalance(Double balance, UserDetailsImpl user) для уменьшения баланса юзера
    //TODO сделать метод setVip(Long postId, int days) для поста с возможностью продления статуса - возвращает PostResponseDto
    //TODO сделать метод checkVip() который работает по рассписанию и снимает VIP статус


}
