package com.visal.FashionEcommerce.modules.discount.mapper;

import com.visal.FashionEcommerce.modules.discount.dto.request.DiscountRequestDTO;
import com.visal.FashionEcommerce.modules.discount.dto.response.DiscountResponseDTO;
import com.visal.FashionEcommerce.modules.discount.model.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper {

    public Discount toEntity(DiscountRequestDTO requestDTO){

        Discount discount = new Discount();
        discount.setName(requestDTO.getName());
        discount.setDiscountType(requestDTO.getDiscountType());
        discount.setDiscountValue(requestDTO.getDiscountValue());
        discount.setStatus(requestDTO.getStatus());
        discount.setStartDate(requestDTO.getStartDate());
        discount.setEndDate(requestDTO.getEndDate());

        return discount;
    }
    
    public DiscountResponseDTO toResponse(Discount discount){
        return DiscountResponseDTO.builder()
                .id(discount.getId())
                .name(discount.getName())
                .discountType(discount.getDiscountType())
                .discountValue(discount.getDiscountValue())
                .status(discount.getStatus())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .createAt(discount.getCreateAt())
                .updatedAt(discount.getUpdatedAt()).build();
    }

}
