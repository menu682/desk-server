package pp.ua.lomax.desk.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.security.UserResponseDto;
import pp.ua.lomax.desk.dto.vip.AddBalanceDto;
import pp.ua.lomax.desk.service.VipService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/vip")
public class VipController {

    private final VipService vipService;

    public VipController(VipService vipService) {
        this.vipService = vipService;
    }

    @PostMapping("/addbalance")
    public UserResponseDto addBalance(@RequestBody AddBalanceDto addBalanceDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return vipService.addBalance(addBalanceDto.getBalance(), userDetailsImpl);
    }



}
