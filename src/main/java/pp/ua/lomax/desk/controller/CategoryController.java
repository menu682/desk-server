package pp.ua.lomax.desk.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.lomax.desk.dto.category.CategoryResponseDto;
import pp.ua.lomax.desk.service.CategoryServise;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryServise categoryServise;

    public CategoryController(CategoryServise categoryServise) {
        this.categoryServise = categoryServise;
    }

    @GetMapping
    public List<CategoryResponseDto> getAllCategories(){
        return categoryServise.getAllCategories();
    }

}
