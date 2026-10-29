package com.visal.FashionEcommerce.modules.brand.service.impl;

import com.visal.FashionEcommerce.common.exception.BadRequestException;
import com.visal.FashionEcommerce.common.exception.ConflictException;
import com.visal.FashionEcommerce.common.exception.ResourceNotFoundException;
import com.visal.FashionEcommerce.modules.brand.dto.request.BrandRequestDTO;
import com.visal.FashionEcommerce.modules.brand.dto.response.BrandResponseDTO;
import com.visal.FashionEcommerce.modules.brand.mapper.BrandMapper;
import com.visal.FashionEcommerce.modules.brand.model.Brand;
import com.visal.FashionEcommerce.modules.brand.repository.BrandRepository;
import com.visal.FashionEcommerce.modules.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO requestDTO) {

        if(requestDTO.getName().isEmpty() || requestDTO.getName().isBlank()){
            throw new BadRequestException("Brand name cannot be empty!");
        }
        if(brandRepository.existsByName(requestDTO.getName())){
            throw new ConflictException("Brand name already exists!");
        }

        Brand brand = brandMapper.toEntity(requestDTO);
        Brand savedBrand = brandRepository.save(brand);

        return brandMapper.toResponse(savedBrand);
    }

    @Override
    public BrandResponseDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("Brand not found with ID: "+ id)
        );
        return brandMapper.toResponse(brand);
    }

    @Override
    public Page<BrandResponseDTO> getAllBrands(Pageable pageable) {

        Page<Brand> brands = brandRepository.findAll(pageable);

        return brands.map(brandMapper::toResponse);
    }

    @Override
    public BrandResponseDTO updateBrand(Long id, BrandRequestDTO requestDTO) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found with ID: "+ id)
        );

        if(requestDTO.getName().isBlank()){
            throw new BadRequestException("Brand cannot be empty!");
        }

        if(brandRepository.existsByName(requestDTO.getName())){
            throw new ConflictException("Brand name already exists!");
        }

        brand.setName(requestDTO.getName());
        brand.setDescription(requestDTO.getDescription());

        Brand savedBrand = brandRepository.save(brand);

        return brandMapper.toResponse(savedBrand);
    }

    @Override
    public void deleteBrand(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Brand not found with ID: " + id)
                        );
        brandRepository.delete(brand);
    }

    @Override
    public Page<BrandResponseDTO> searchBrands(String keyword, Pageable pageable) {

        Page<Brand> brands = brandRepository.findByNameContainingIgnoreCase(
                keyword,
                pageable
        );
        return brands.map(brandMapper::toResponse);
    }
}
