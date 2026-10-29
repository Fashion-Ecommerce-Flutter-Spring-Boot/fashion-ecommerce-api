package com.visal.FashionEcommerce.modules.brand.repository;

import com.visal.FashionEcommerce.modules.brand.dto.response.BrandResponseDTO;
import com.visal.FashionEcommerce.modules.brand.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Boolean existsByName(String name);

    Page<Brand> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

}
