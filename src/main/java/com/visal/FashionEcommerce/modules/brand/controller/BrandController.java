package com.visal.FashionEcommerce.modules.brand.controller;

import com.visal.FashionEcommerce.common.response.ApiResponse;
import com.visal.FashionEcommerce.common.response.ApiResponseUtil;
import com.visal.FashionEcommerce.modules.brand.dto.request.BrandRequestDTO;
import com.visal.FashionEcommerce.modules.brand.dto.response.BrandResponseDTO;
import com.visal.FashionEcommerce.modules.brand.service.BrandService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/brands")
@Tag(name = "Brand API")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponseDTO>> createBrand(
            @Valid @RequestBody BrandRequestDTO requestDTO
    ){
        BrandResponseDTO brand =  brandService.createBrand(requestDTO);
        ApiResponse<BrandResponseDTO> response = ApiResponseUtil.success(
                "Brand created successfully",
                brand
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> getBrandById(
            @PathVariable Long id)
    {
        BrandResponseDTO brand = brandService.getBrandById(id);
        ApiResponse<BrandResponseDTO> response = ApiResponseUtil.success(
                "Brand retrieved successfully",
                brand
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BrandResponseDTO>>> getAllBrands(
            Pageable pageable
    ){
        Page<BrandResponseDTO> brands = brandService.getAllBrands(pageable);

        ApiResponse<Page<BrandResponseDTO>> response = ApiResponseUtil.success(
                "Brand retrieved successfully",
                brands
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandRequestDTO requestDTO
    ){
        BrandResponseDTO updatedBrand = brandService.updateBrand(id, requestDTO);

        ApiResponse<BrandResponseDTO> response = ApiResponseUtil.success(
                "Brand updated successfully!",
                updatedBrand
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> deleteBrand(
            @PathVariable Long id){

        brandService.deleteBrand(id);

        ApiResponse<BrandResponseDTO> response = ApiResponseUtil.success(
                "Brand deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<BrandResponseDTO>>> searchBrands(
            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ){
        Page<BrandResponseDTO> searchedBrands = brandService.searchBrands(
                keyword, pageable
        );
        ApiResponse<Page<BrandResponseDTO>> response = ApiResponseUtil.success(
                "Brands retrieved successfully",
                searchedBrands
        );
        return ResponseEntity.ok(response);
    }

}
