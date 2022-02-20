package pp.ua.lomax.desk.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.lomax.desk.dto.security.JwtResponseDTO;
import pp.ua.lomax.desk.dto.security.LoginRequestDTO;
import pp.ua.lomax.desk.dto.MessageResponseDTO;
import pp.ua.lomax.desk.dto.security.SignupRequestDTO;
import pp.ua.lomax.desk.service.security.AuthService;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/auth/")
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return authService.loginUser(loginRequestDTO);
    }

    @PostMapping("/register")
    public MessageResponseDTO register(@Valid @RequestBody SignupRequestDTO signupRequestDTO){
        return authService.registerUser(signupRequestDTO);
    }

}