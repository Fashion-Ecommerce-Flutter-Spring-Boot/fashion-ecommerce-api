package com.visal.FashionEcommerce.modules.brand.mapper;

import com.visal.FashionEcommerce.modules.brand.dto.request.BrandRequestDTO;
import com.visal.FashionEcommerce.modules.brand.dto.response.BrandResponseDTO;
import com.visal.FashionEcommerce.modules.brand.model.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    public Brand toEntity(BrandRequestDTO requestDTO){
        Brand brand = new Brand();
        brand.setName(requestDTO.getName());
        brand.setDescription(requestDTO.getDescription());
        return brand;
    }

    public BrandResponseDTO toResponse(Brand brand){
        return BrandResponseDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .status(brand.isStatus())
                .createAt(brand.getCreateAt())
                .updateAt(brand.getUpdateAt())
                .build();
    }
}
