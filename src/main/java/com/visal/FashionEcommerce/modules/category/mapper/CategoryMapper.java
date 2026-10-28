package com.visal.FashionEcommerce.modules.category.mapper;

import com.visal.FashionEcommerce.modules.category.dto.request.CategoryRequestDTO;
import com.visal.FashionEcommerce.modules.category.dto.response.CategoryResponseDTO;
import com.visal.FashionEcommerce.modules.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequestDTO requestDTO){
        Category category = new Category();

        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());

        return category;
    }

    public CategoryResponseDTO toResponse(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .parentId(
                        category.getParentId() != null
                                ? category.getParentId().getId()
                                : null

                )
                .parentName(
                        category.getParentId() != null
                                ? category.getParentId().getName()
                                : null
                )
                .name(category.getName())
                .description(category.getDescription())
                .status(category.isStatus())
                .createAt(category.getCreateAt())
                .updateAt(category.getUpdateAt())
                .build();

    }
 }
