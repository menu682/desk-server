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
import pp.ua.lomax.desk.dto.region.RegionAddDto;
import pp.ua.lomax.desk.dto.region.RegionDeleteDto;
import pp.ua.lomax.desk.dto.region.RegionPutDto;
import pp.ua.lomax.desk.dto.region.RegionResponseDto;
import pp.ua.lomax.desk.service.RegionService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/countries")
    public List<RegionResponseDto> getAllCountries(){
        return regionService.getRegionsByParent(0L);
    }

    @GetMapping("/parent/{parentId}")
    public List<RegionResponseDto> getAllRegionsByParent(@PathVariable Long parentId){
        return regionService.getRegionsByParent(parentId);
    }

    @GetMapping
    public List<RegionResponseDto> getAllRegions(){
        return regionService.getAllRegions();
    }

    @GetMapping("/region/{regionId}")
    public RegionResponseDto getRegionById(@PathVariable Long regionId){
        return regionService.getRegionById(regionId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public RegionResponseDto addRegion(@RequestBody RegionAddDto regionAddDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return regionService.addRegion(regionAddDto, userDetailsImpl);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public RegionResponseDto putRegion(@RequestBody RegionPutDto regionPutDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return regionService.putRegion(regionPutDto, userDetailsImpl);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public MessageResponseDto deleteRegion(@RequestBody RegionDeleteDto regionDeleteDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return regionService.deleteRegion(regionDeleteDto, userDetailsImpl);
    }


}
