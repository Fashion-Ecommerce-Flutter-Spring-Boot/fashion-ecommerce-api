package com.visal.FashionEcommerce.modules.discount.service;


import com.visal.FashionEcommerce.modules.discount.dto.request.DiscountRequestDTO;
import com.visal.FashionEcommerce.modules.discount.dto.response.DiscountResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountService {

    DiscountResponseDTO createDiscount(DiscountRequestDTO requestDTO);
    DiscountResponseDTO getDiscountById(Long id);
    Page<DiscountResponseDTO> getAllDiscounts(Pageable pageable);
    DiscountResponseDTO updateDiscount(Long id, DiscountRequestDTO requestDTO);
    void deleteDiscount(Long id);

}
