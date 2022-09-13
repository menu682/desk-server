package pp.ua.lomax.desk.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pp.ua.lomax.desk.config.security.UserDetailsImpl;
import pp.ua.lomax.desk.dto.EResponseMessage;
import pp.ua.lomax.desk.dto.MessageResponseDto;
import pp.ua.lomax.desk.dto.category.CategoryAddDto;
import pp.ua.lomax.desk.dto.category.CategoryDeleteDto;
import pp.ua.lomax.desk.dto.category.CategoryPutDto;
import pp.ua.lomax.desk.dto.category.CategoryResponseDto;
import pp.ua.lomax.desk.exeptions.ConflictException;
import pp.ua.lomax.desk.exeptions.DataNotFoundException;
import pp.ua.lomax.desk.exeptions.EExceptionMessage;
import pp.ua.lomax.desk.persistance.entity.CategoryEntity;
import pp.ua.lomax.desk.persistance.entity.PostEntity;
import pp.ua.lomax.desk.persistance.repository.CategoryRepository;
import pp.ua.lomax.desk.persistance.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
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

    public List<CategoryResponseDto> getAllCategories() {

        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        return categoryEntityList.stream().map(this::convertCategoryEntityToDto).toList();

    }

    public CategoryResponseDto getCategoryById(Long categoryId) {

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(categoryId)
                .orElseThrow(() ->
                        new DataNotFoundException(EExceptionMessage.CATEGORY_NO_SUCH.getMessage()));

        return convertCategoryEntityToDto(categoryEntity);
    }

    public CategoryResponseDto addCategory(CategoryAddDto categoryAddDto,
                                           UserDetailsImpl userDetailsImpl) {

        Optional<CategoryEntity> findCategoryEntity =
                categoryRepository.findCategoryEntityByNameAndParent(
                        categoryAddDto.getName(), categoryAddDto.getParent());

        if (findCategoryEntity.isPresent()) {
            throw new ConflictException(EExceptionMessage.CATEGORY_ALREADY_EXISTS.getMessage());
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryAddDto.getName());
        categoryEntity.setParent(categoryAddDto.getParent());
 //       categoryEntity.setUser(userDetailsImpl.getUser());

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        log.info("Add category: " + savedCategoryEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return convertCategoryEntityToDto(savedCategoryEntity);
    }

    public CategoryResponseDto putCategory(CategoryPutDto categoryPutDto,
                                           UserDetailsImpl userDetailsImpl) {

        CategoryEntity categoryEntity =
                categoryRepository.findCategoryEntityById(categoryPutDto.getId())
                        .orElseThrow(() ->
                                new DataNotFoundException(EExceptionMessage.CATEGORY_NO_SUCH.getMessage()));

        categoryEntity.setName(categoryPutDto.getName());
        categoryEntity.setParent(categoryEntity.getParent());
 //       categoryEntity.setUser(userDetailsImpl.getUser());

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        log.info("Put category: " + categoryEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return convertCategoryEntityToDto(savedCategoryEntity);
    }

    public MessageResponseDto deleteCategory(CategoryDeleteDto categoryDeleteDto,
                                             UserDetailsImpl userDetailsImpl) {

        List<CategoryEntity> parentCategoryEntity =
                categoryRepository.findCategoryEntityByParent(categoryDeleteDto.getId());

        if (!parentCategoryEntity.isEmpty()) {
            throw new ConflictException(EExceptionMessage.CATEGORY_IS_PARENT.getMessage());
        }

        CategoryEntity categoryEntity =
                categoryRepository.findCategoryEntityById(categoryDeleteDto.getId())
                        .orElseThrow(() ->
                                new DataNotFoundException(EExceptionMessage.CATEGORY_NO_SUCH.getMessage()));

        List<PostEntity> postEntityList = postRepository.findByCategory(categoryEntity);

        if(!postEntityList.isEmpty()){
            throw new ConflictException(EExceptionMessage.CATEGORY_IS_NOT_EMPTY.getMessage());
        }

        categoryRepository.delete(categoryEntity);

        log.info("Delete category: " + categoryEntity.getName() + "; user: " + userDetailsImpl.getUsername());

        return new MessageResponseDto(EResponseMessage.CATEGORY_DELETED.getMessage());
    }

}
