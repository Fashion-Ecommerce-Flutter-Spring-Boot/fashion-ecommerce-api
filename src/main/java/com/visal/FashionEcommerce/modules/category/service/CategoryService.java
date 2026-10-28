package com.visal.FashionEcommerce.modules.category.service;

import com.visal.FashionEcommerce.modules.category.dto.request.CategoryRequestDTO;
import com.visal.FashionEcommerce.modules.category.dto.response.CategoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

    CategoryResponseDTO getCategoryById(Long id);

    Page<CategoryResponseDTO> getAllCategories(Pageable pageable);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);

    void deleteCategory(Long id);

    Page<CategoryResponseDTO> searchCategory(
            String keyword,
            Pageable pageable
    );

    List<CategoryResponseDTO> getMainCategories();

}
