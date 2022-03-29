package pp.ua.lomax.desk.service;


import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.category.CategoryAddDto;
import pp.ua.lomax.desk.dto.category.CategoryResponseDto;
import pp.ua.lomax.desk.exeptions.NoFindRuntimeExeption;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.repository.CategoryRepository;

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

    public CategoryResponseDto addCategory(CategoryAddDto categoryAddDto){

        Optional<CategoryEntity> findCategoryEntity =
                categoryRepository.findCategoryEntityByNameAndParent(
                categoryAddDto.getName(), categoryAddDto.getParent());

        if(findCategoryEntity.isPresent()){
            throw new NoFindRuntimeExeption("Такая категория уже есть!");
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryAddDto.getName());
        categoryEntity.setParent(categoryAddDto.getParent());

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        return convertCategoryEntityToDto(savedCategoryEntity);
    }


}
