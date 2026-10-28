package com.visal.FashionEcommerce.modules.category.service.impl;

import com.visal.FashionEcommerce.common.exception.BadRequestException;
import com.visal.FashionEcommerce.common.exception.ConflictException;
import com.visal.FashionEcommerce.common.exception.ResourceNotFoundException;
import com.visal.FashionEcommerce.modules.category.dto.request.CategoryRequestDTO;
import com.visal.FashionEcommerce.modules.category.dto.response.CategoryResponseDTO;
import com.visal.FashionEcommerce.modules.category.mapper.CategoryMapper;
import com.visal.FashionEcommerce.modules.category.model.Category;
import com.visal.FashionEcommerce.modules.category.repository.CategoryRepository;
import com.visal.FashionEcommerce.modules.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // Default message
    private Category getCategoryEntityById(Long id) {

        return getCategoryEntityById(
                id,
                "Category not found with ID: "
        );
    }

    // Custom message
    private Category getCategoryEntityById(Long id, String message) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        message + id
                ));
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {

        if(requestDTO.getName() == null ||
                requestDTO.getName().isBlank()){
            throw new BadRequestException("Category name cannot be empty!");
        }

        if(categoryRepository.existsByName(requestDTO.getName())){
            throw new ConflictException("Category name already exists!");
        }

        Category category = categoryMapper.toEntity(requestDTO);

        if(requestDTO.getParentId() != null){
            Category parentCategoryId = categoryRepository.findById(
                    requestDTO.getParentId()
            ).orElseThrow(()->
                    new ResourceNotFoundException("Main category not found!"));

            category.setParentId(parentCategoryId);
        }
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {

        return categoryMapper.toResponse(
                getCategoryEntityById(id)
        );
    }

    @Override
    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {

        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(categoryMapper::toResponse);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {

        Category category = getCategoryEntityById(id);

        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());

        if(requestDTO.getParentId() != null){
            if(requestDTO.getParentId().equals(id)){
                throw new BadRequestException("A category cannot be it's own parent");
            }
            Category parentCategory = getCategoryEntityById(
                    requestDTO.getParentId(),
                    "Main category not found with ID: ");

            category.setParentId(parentCategory);
        }else {
            category.setParentId(null);
        }

        Category updateCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(updateCategory);
    }

    @Override
    public void deleteCategory(Long id) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Category not found with ID: " + id)
//        );
        Category category = getCategoryEntityById(id);

        categoryRepository.delete(category);
    }

    @Override
    public Page<CategoryResponseDTO> searchCategory(String keyword, Pageable pageable) {

        Page<Category> categories = categoryRepository.findByNameContainingIgnoreCaseAndStatusTrue(
                keyword,
                pageable
        );

        return categories.map(categoryMapper::toResponse);
    }

    @Override
    public List<CategoryResponseDTO> getMainCategories() {

        List<Category> categories = categoryRepository.findByParentIdIsNullAndStatusTrue();

        return categories.stream()
                .map(categoryMapper::toResponse).toList();
    }
}
