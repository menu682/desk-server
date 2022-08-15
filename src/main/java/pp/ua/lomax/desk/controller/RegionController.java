package pp.ua.lomax.desk.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.lomax.desk.dto.region.RegionResponseDto;
import pp.ua.lomax.desk.service.RegionService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public List<RegionResponseDto> getAllRegions(){
        return regionService.getAllRegions();
    }

    @GetMapping("/{region_id}")
    public RegionResponseDto getRegionById(@PathVariable Long region_id){
        return regionService.getRegionById(region_id);
    }

    @GetMapping("/country/{country_id}")
    public List<RegionResponseDto> getAllRegionsByCountry(@PathVariable Long country_id){
        return regionService.getRegionsByCountry(country_id);
    }

    //TODO addRegion
    //TODO putRegion
    //TODO delRegion


}
