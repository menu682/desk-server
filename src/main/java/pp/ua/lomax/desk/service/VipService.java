package pp.ua.lomax.desk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.security.UserResponseDto;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.PostRepository;
import pp.ua.lomax.desk.persistance.repository.security.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class VipService {

    PostRepository postRepository;
    UserRepository userRepository;

    public VipService(PostRepository postRepository, UserRepository userRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private UserResponseDto convertUserEntityToDto(UserEntity userEntity){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userEntity.getId());
        userResponseDto.setUsername(userEntity.getUsername());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setPhone(userEntity.getPhone());
        userResponseDto.setStatus(userEntity.getStatus());
        userResponseDto.setBalance(userEntity.getBalance());

        return userResponseDto;
    }

    public UserResponseDto addBalance(Double balance, UserDetailsImpl userDetailsImpl){

        UserEntity user = userDetailsImpl.getUser();

        Double currentBalance;

        if(user.getBalance() == null){
            currentBalance = 0d;
        } else {
            currentBalance = user.getBalance();
        }

        currentBalance += balance;

        user.setBalance(currentBalance);

        userRepository.save(user);

        log.info("User id " + user.getId() + " "
                + user.getUsername() + " add balance "
                + balance + " Current balance " + currentBalance);

        return convertUserEntityToDto(user);

    }

    //TODO сделать метод changeBalance(Double balance, UserDetailsImpl user) для уменьшения баланса юзера
    //TODO сделать метод setVip(Long postId, int days) для поста с возможностью продления статуса - возвращает PostResponseDto
    //TODO сделать метод checkVip() который работает по рассписанию и снимает VIP статус


}
