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
import pp.ua.lomax.desk.dto.category.CategoryAddDto;
import pp.ua.lomax.desk.dto.category.CategoryDeleteDto;
import pp.ua.lomax.desk.dto.category.CategoryPutDto;
import pp.ua.lomax.desk.dto.category.CategoryResponseDto;
import pp.ua.lomax.desk.service.CategoryServise;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryServise categoryServise;

    public CategoryController(CategoryServise categoryServise) {
        this.categoryServise = categoryServise;
    }

    @GetMapping
    public List<CategoryResponseDto> getAllCategories(){
        return categoryServise.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryResponseDto getCategoryById(@PathVariable Long categoryId){
        return categoryServise.getCategoryById(categoryId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/")
    public CategoryResponseDto addCategory(@RequestBody CategoryAddDto categoryAddDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return categoryServise.addCategory(categoryAddDto, userDetailsImpl);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/")
    public CategoryResponseDto putCategory(@RequestBody CategoryPutDto categoryPutDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return categoryServise.putCategory(categoryPutDto, userDetailsImpl);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/")
    public MessageResponseDto deleteCategory(@RequestBody CategoryDeleteDto categoryDeleteDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return categoryServise.deleteCategory(categoryDeleteDto, userDetailsImpl);
    }

}
