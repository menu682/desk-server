package pp.ua.lomax.desk.service;


import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.category.CategoryAddDto;
import pp.ua.lomax.desk.dto.category.CategoryDeleteDto;
import pp.ua.lomax.desk.dto.category.CategoryPutDto;
import pp.ua.lomax.desk.dto.category.CategoryResponseDto;
import pp.ua.lomax.desk.exeptions.NoFindRuntimeExeption;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServise {

    private final CategoryRepository categoryRepository;

    public CategoryServise(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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

    public List<CategoryResponseDto> getAllCategories(){

        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        return categoryEntityList.stream().map(this::convertCategoryEntityToDto).toList();

    }

    public CategoryResponseDto getCategoryById(Long categoryId){

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(categoryId)
                .orElseThrow(() ->
                    new RuntimeException("Error: no such category"));

        return convertCategoryEntityToDto(categoryEntity);
    }

    public CategoryResponseDto addCategory(CategoryAddDto categoryAddDto){

        Optional<CategoryEntity> findCategoryEntity =
                categoryRepository.findCategoryEntityByNameAndParent(
                categoryAddDto.getName(), categoryAddDto.getParent());

        if(findCategoryEntity.isPresent()){
            throw new NoFindRuntimeExeption("Error: this category already exists");
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryAddDto.getName());
        categoryEntity.setParent(categoryAddDto.getParent());

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        return convertCategoryEntityToDto(savedCategoryEntity);
    }

    public CategoryResponseDto putCategory(CategoryPutDto categoryPutDto){

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(categoryPutDto.getId())
                .orElseThrow(() ->
                        new RuntimeException("Error: no such category"));

        categoryEntity.setName(categoryPutDto.getName());
        categoryEntity.setParent(categoryEntity.getParent());

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        return convertCategoryEntityToDto(savedCategoryEntity);
    }

    public MessageResponseDto deleteCategory(CategoryDeleteDto categoryDeleteDto){

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(categoryDeleteDto.getId())
                .orElseThrow(() ->
                        new RuntimeException("Error: no such category"));

        categoryRepository.delete(categoryEntity);
        return new MessageResponseDto("Category deleted");
    }

}
