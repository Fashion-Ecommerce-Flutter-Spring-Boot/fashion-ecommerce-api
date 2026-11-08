package com.visal.FashionEcommerce.modules.product.service;

import com.visal.FashionEcommerce.modules.product.dto.request.ProductRequestDTO;
import com.visal.FashionEcommerce.modules.product.dto.response.ProductResponseDTO;
import com.visal.FashionEcommerce.modules.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO requestDTO, List<MultipartFile> images);

    ProductResponseDTO getProductById(Long id);

    Page<ProductResponseDTO> getAllProducts(Pageable pageable);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);

    void deleteProduct(Long id);

    Page<ProductResponseDTO> searchProducts(String keyword, Pageable pageable);

    Page<ProductResponseDTO> filterProducts(
            String keyword,
            ProductStatus status,
            Long categoryId,
            Long brandId,
            Long discountId,
            Pageable pageable
    );

    Page<ProductResponseDTO> filterProductByStatus(ProductStatus status, Pageable pageable);

    Page<ProductResponseDTO> filterProductByCategory(Long categoryId, Pageable pageable);

    Page<ProductResponseDTO> filterProductByBrand(Long brandId, Pageable pageable);

}
