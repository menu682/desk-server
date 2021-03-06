package pp.ua.lomax.desk.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.lomax.desk.dto.security.JwtResponseDto;
import pp.ua.lomax.desk.dto.security.LoginRequestDto;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.security.SignupRequestDto;
import pp.ua.lomax.desk.service.security.AuthService;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDTO){
        return authService.loginUser(loginRequestDTO);
    }

    @PostMapping("/register")
    public MessageResponseDto register(@Valid @RequestBody SignupRequestDto signupRequestDTO){
        return authService.registerUser(signupRequestDTO);
    }

}