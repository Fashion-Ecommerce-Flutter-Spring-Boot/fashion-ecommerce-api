package com.visal.FashionEcommerce.modules.category.controller;

import com.visal.FashionEcommerce.common.response.ApiResponse;
import com.visal.FashionEcommerce.common.response.ApiResponseUtil;
import com.visal.FashionEcommerce.modules.category.dto.request.CategoryRequestDTO;
import com.visal.FashionEcommerce.modules.category.dto.response.CategoryResponseDTO;
import com.visal.FashionEcommerce.modules.category.model.Category;
import com.visal.FashionEcommerce.modules.category.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "Category API ")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(
            @Valid @RequestBody CategoryRequestDTO requestDTO
    ){
        CategoryResponseDTO category =
                categoryService.createCategory(requestDTO);

        ApiResponse<CategoryResponseDTO> response =
                 ApiResponseUtil.created(
                        "Category Created successfully",
                        category
        );

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(
            @PathVariable Long id
    ){
        CategoryResponseDTO category = categoryService.getCategoryById(id);

        ApiResponse<CategoryResponseDTO> response = ApiResponseUtil.success(
                "Category retrieved successfully!",
                category
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CategoryResponseDTO>>> getAllCategories(
        Pageable pageable
    ){
        Page<CategoryResponseDTO> categories = categoryService.getAllCategories(pageable);

        ApiResponse<Page<CategoryResponseDTO>> response = ApiResponseUtil.success(
                "Categories retrieved successfully",
                categories
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO requestDTO
    ){
        CategoryResponseDTO updatedCategory =
                categoryService.updateCategory(id, requestDTO);

        ApiResponse<CategoryResponseDTO> response = ApiResponseUtil.success(
                "Category updated successfully",
                updatedCategory
        );

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable Long id
    ){
        categoryService.deleteCategory(id);

        ApiResponse<Void> response = ApiResponseUtil.success(
                "Category deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/main")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getMainCategories(){

        List<CategoryResponseDTO> categories =  categoryService.getMainCategories();

        ApiResponse<List<CategoryResponseDTO>> response = ApiResponseUtil.success(
                "Main categories retrieved successfully",
                categories
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<CategoryResponseDTO>>> search(
        @RequestParam(defaultValue = "") String keyword,
        @PageableDefault(
            page = 0,
                size = 10,
                //sort = "create_at",
                direction = Sort.Direction.DESC
        ) Pageable pageable
    ){
        Page<CategoryResponseDTO> categories = categoryService.searchCategory(
                keyword,
                pageable
        );

        ApiResponse<Page<CategoryResponseDTO>> response = ApiResponseUtil.success(
                "Result category for " + keyword,
                categories
        );

        return ResponseEntity.ok(response);
    }
}
