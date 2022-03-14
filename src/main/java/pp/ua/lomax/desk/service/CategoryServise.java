package pp.ua.lomax.desk.service;


import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.dto.category.CategoryResponseDto;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServise {

    private final CategoryRepository categoryRepository;

    public CategoryServise(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> getAllCategories(){

        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        return categoryEntityList.stream().map(this::convertCategoryEntityToDto).toList();

    }

    public CategoryResponseDto getCategoryById(Long categoryId){

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(categoryId)
                .orElseThrow(() ->
                    new RuntimeException("Такой категории нет!"));

        return convertCategoryEntityToDto(categoryEntity);
    }

    private CategoryResponseDto convertCategoryEntityToDto(CategoryEntity categoryEntity) {

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(categoryEntity.getId());
        categoryResponseDto.setCreated(categoryEntity.getCreated());
        categoryResponseDto.setUpdated(categoryEntity.getUpdated());
        categoryResponseDto.setName(categoryEntity.getName());
        categoryResponseDto.setParent(categoryEntity.getParent());

        return categoryResponseDto;
    }

}
