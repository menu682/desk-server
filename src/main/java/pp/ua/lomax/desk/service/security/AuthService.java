package pp.ua.lomax.desk.service.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.dto.EResponseMessage;
import pp.ua.lomax.desk.dto.security.JwtResponseDto;
import pp.ua.lomax.desk.dto.security.LoginRequestDto;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.security.SignupRequestDto;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.config.security.jwt.JwtUtils;
import pp.ua.lomax.desk.exeptions.EExceptionMessage;
import pp.ua.lomax.desk.exeptions.MessageRuntimeException;
import pp.ua.lomax.desk.persistance.ERole;
import pp.ua.lomax.desk.persistance.EUserStatus;
import pp.ua.lomax.desk.persistance.entity.security.RoleEntity;
import pp.ua.lomax.desk.persistance.entity.security.UserEntity;
import pp.ua.lomax.desk.persistance.repository.security.RoleRepository;
import pp.ua.lomax.desk.persistance.repository.security.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder encoder,
                       JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponseDto loginUser(LoginRequestDto loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshJwt = jwtUtils.generateJwtRefreshToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();

        return new JwtResponseDto(
                jwt,
                refreshJwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        );
    }


    public MessageResponseDto registerUser(SignupRequestDto signupRequestDTO) {

        if (signupRequestDTO.getUsername().isEmpty()
                || signupRequestDTO.getPassword().isEmpty()
                || signupRequestDTO.getEmail().isEmpty()) {
            throw new MessageRuntimeException(EExceptionMessage.FIELDS_MUST_NOT_BE_EMPTY.getMessage());
        }

        if (userRepository.existsByUsername(signupRequestDTO.getUsername())) {
            throw new MessageRuntimeException(EExceptionMessage.NAME_IS_ALREADY_TAKEN.getMessage());
        }

        if (userRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new MessageRuntimeException(EExceptionMessage.EMAIL_IS_ALREADY_TAKEN.getMessage());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signupRequestDTO.getUsername());
        userEntity.setEmail(signupRequestDTO.getEmail());
        userEntity.setPhone(signupRequestDTO.getPhone());
        userEntity.setPassword(encoder.encode(signupRequestDTO.getPassword()));
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new MessageRuntimeException(EExceptionMessage.NO_SUCH_ROLE.getMessage()));

        roles.add(userRole);

        userEntity.setStatus(EUserStatus.ACTIVE);
        userEntity.setRoles(roles);
        //TODO ?????????? ?????????? ???????? ???????????? ???????? ?????????????? ?????? ?????????? ?????? ???????? ?? ???????? - ????????????????
        userRepository.save(userEntity);
        return new MessageResponseDto(EResponseMessage.REGISTER_SUCCESSFULLY.getMessage());

    }

}
