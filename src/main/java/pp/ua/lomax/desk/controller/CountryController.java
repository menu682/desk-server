package pp.ua.lomax.desk.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.country.CountryAddDto;
import pp.ua.lomax.desk.dto.country.CountryDeleteDto;
import pp.ua.lomax.desk.dto.country.CountryPutDto;
import pp.ua.lomax.desk.dto.country.CountryResponseDto;
import pp.ua.lomax.desk.service.CountryService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<CountryResponseDto> getAllCountries(){
        return countryService.getAllCountries();
    }

    @GetMapping("/{country_id}")
    public CountryResponseDto getCountryById(@PathVariable Long country_id){
        return countryService.getCountryById(country_id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/")
    public CountryResponseDto addCountry(@RequestBody CountryAddDto countryAddDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return countryService.addCountry(countryAddDto, userDetailsImpl);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/")
    public CountryResponseDto putCountry(@RequestBody CountryPutDto countryPutDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return countryService.putCountry(countryPutDto, userDetailsImpl);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/")
    public MessageResponseDto deleteCountry(@RequestBody CountryDeleteDto countryDeleteDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return countryService.deleteCountry(countryDeleteDto, userDetailsImpl);
    }

}
