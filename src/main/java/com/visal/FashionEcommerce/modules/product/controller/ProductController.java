package com.visal.FashionEcommerce.modules.product.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.visal.FashionEcommerce.common.response.ApiResponse;
import com.visal.FashionEcommerce.common.response.ApiResponseUtil;
import com.visal.FashionEcommerce.modules.product.dto.request.ProductRequestDTO;
import com.visal.FashionEcommerce.modules.product.dto.response.ProductResponseDTO;
import com.visal.FashionEcommerce.modules.product.enums.ProductStatus;
import com.visal.FashionEcommerce.modules.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product API")
public class ProductController {
    private  final ProductService productService;
    private final ObjectMapper objectMapper;

    /*
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(
            //@RequestPart("product") @Valid ProductRequestDTO requestDTO,
            @RequestPart("product") String productJson,
            @RequestPart("images")List<MultipartFile> images
            )throws JsonProcessingException {
        ProductRequestDTO request =
                objectMapper.readValue(productJson, ProductRequestDTO.class);

        ProductResponseDTO  product = productService.createProduct(request, images);
        ApiResponse<ProductResponseDTO> response = ApiResponseUtil.success(
                "Product created successfully",
                product
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    */

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(
            @ModelAttribute @Valid ProductRequestDTO requestDTO,
            @RequestPart("images") List<MultipartFile> images

    ) {
        ProductResponseDTO response =
                productService.createProduct(requestDTO, images);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseUtil.success(
                        "Product created successfully",
                        response
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(
            @PathVariable Long id
    ){
        ProductResponseDTO product = productService.getProductById(id);
        ApiResponse<ProductResponseDTO> response = ApiResponseUtil.success(
                "Product retrieve successfully",
                product
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> getAllProducts(
            Pageable pageable
    ){
        Page<ProductResponseDTO> products = productService.getAllProducts(pageable);
        ApiResponse<Page<ProductResponseDTO>> response = ApiResponseUtil.success(
                "Product retrieve successfully",
                products
        );
        return  ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
           @PathVariable Long id, @Valid @RequestBody ProductRequestDTO requestDTO
    ){
        ProductResponseDTO product = productService.updateProduct(id, requestDTO);
        ApiResponse<ProductResponseDTO> response = ApiResponseUtil.success(
                "Product updated successfully",
                product
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable Long id
    ){
        productService.deleteProduct(id);
        ApiResponse<Void> response = ApiResponseUtil.success(
                "Product deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> searchProducts(
           @RequestParam(defaultValue = "") String keyword,
           @PageableDefault(
                   size = 15,
                   direction = Sort.Direction.ASC
           )
           Pageable pageable
    ){
        Page<ProductResponseDTO> products = productService.searchProducts(
                keyword, pageable
        );

        ApiResponse<Page<ProductResponseDTO>> response = ApiResponseUtil.success(
                "Product retrieve successfully",
                products
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> filterProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long discountId,
            @PageableDefault(
                    sort = "createAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ){
        Page<ProductResponseDTO> products = productService.filterProducts(
                keyword,
                status,
                categoryId,
                brandId,
                discountId,
                pageable
        );
        ApiResponse<Page<ProductResponseDTO>> response = ApiResponseUtil.success(
                "Product filtered successfully",
                products
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter/status")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> filterProductsByStatus(
            @RequestParam ProductStatus status,
            @PageableDefault(
                    sort = "createAt"
            )
            Pageable pageable
    ){
        Page<ProductResponseDTO> products = productService.filterProductByStatus(status, pageable);
        ApiResponse<Page<ProductResponseDTO>> response = ApiResponseUtil.success(
                "Product filtered successfully",
                products
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> filterByCategory(
        @PathVariable Long categoryId,
        @PageableDefault(
                direction = Sort.Direction.DESC
        )
        Pageable pageable
    ){
        Page<ProductResponseDTO> products = productService.filterProductByCategory(categoryId, pageable);
        ApiResponse<Page<ProductResponseDTO>> response = ApiResponseUtil.success(
                "Product filtered by category successfully",
                products
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter/brand/{brandId}")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> filterByBrand(
            @PathVariable Long brandId,
            @PageableDefault(
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ){
        Page<ProductResponseDTO> products = productService.filterProductByBrand(brandId, pageable);

        ApiResponse<Page<ProductResponseDTO>> response = ApiResponseUtil.success(
                "Product filtered by brand successfully",
                products
        );
        return ResponseEntity.ok(response);
    }
}
