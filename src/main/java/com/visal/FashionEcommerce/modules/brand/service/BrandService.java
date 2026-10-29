package com.visal.FashionEcommerce.modules.brand.service;

import com.visal.FashionEcommerce.modules.brand.dto.request.BrandRequestDTO;
import com.visal.FashionEcommerce.modules.brand.dto.response.BrandResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {

    BrandResponseDTO createBrand(BrandRequestDTO requestDTO);

    BrandResponseDTO getBrandById(Long id);

    Page<BrandResponseDTO> getAllBrands(Pageable pageable);

    BrandResponseDTO updateBrand(Long id, BrandRequestDTO requestDTO);

    void deleteBrand(Long id);

    Page<BrandResponseDTO> searchBrands(String keyword, Pageable pageable);

}
