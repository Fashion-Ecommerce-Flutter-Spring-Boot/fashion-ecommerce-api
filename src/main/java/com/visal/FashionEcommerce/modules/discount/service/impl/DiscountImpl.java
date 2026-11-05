package com.visal.FashionEcommerce.modules.discount.service.impl;

import com.visal.FashionEcommerce.common.exception.BadRequestException;
import com.visal.FashionEcommerce.common.exception.ResourceNotFoundException;
import com.visal.FashionEcommerce.modules.discount.dto.request.DiscountRequestDTO;
import com.visal.FashionEcommerce.modules.discount.dto.response.DiscountResponseDTO;
import com.visal.FashionEcommerce.modules.discount.enums.DiscountStatus;
import com.visal.FashionEcommerce.modules.discount.enums.DiscountType;
import com.visal.FashionEcommerce.modules.discount.mapper.DiscountMapper;
import com.visal.FashionEcommerce.modules.discount.model.Discounts;
import com.visal.FashionEcommerce.modules.discount.repository.DiscountRepository;
import com.visal.FashionEcommerce.modules.discount.service.DiscountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class DiscountImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    @Override
    public DiscountResponseDTO createDiscount(DiscountRequestDTO requestDTO) {

        validateDiscount(requestDTO);

        Discounts discounts = discountMapper.toEntity(requestDTO);

        if(discountRepository.existsByNameIgnoreCase(requestDTO.getName())){
            throw  new BadRequestException("Discount name already exists");
        }

        Discounts savedDiscount = discountRepository.save(discounts);

        return discountMapper.toResponse(savedDiscount);
    }

    @Override
    public DiscountResponseDTO getDiscountById(Long id) {
        Discounts discount = discountRepository
                .findById(id).orElseThrow(()->
                        new ResourceNotFoundException(
                                "Discount not found with ID: " + id)
                );
        DiscountResponseDTO response = discountMapper.toResponse(discount);
        response.setStatus(getDiscountStatus(discount));
        return response;
    }

    @Override
    public Page<DiscountResponseDTO> getAllDiscounts(Pageable pageable) {
        Page<Discounts> discounts = discountRepository.findAll(pageable);
        return discounts.map(discountMapper::toResponse);
    }

    @Override
    public DiscountResponseDTO updateDiscount(Long id, DiscountRequestDTO requestDTO) {

        Discounts discount = discountRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        "Discount not found with ID: " + id)
        );
        validateDiscount(requestDTO);

        discount.setName(requestDTO.getName());
        discount.setDiscountType(requestDTO.getDiscountType());
        discount.setDiscountValue(requestDTO.getDiscountValue());
        discount.setStatus(requestDTO.getStatus());
        discount.setStartDate(requestDTO.getStartDate());
        discount.setEndDate(requestDTO.getEndDate());

        Discounts savedDiscounts = discountRepository.save(discount);
        DiscountResponseDTO response = discountMapper.toResponse(savedDiscounts);
        response.setStatus(getDiscountStatus(savedDiscounts));

        return response;
    }

    @Override
    public void deleteDiscount(Long id) {
        Discounts discount = discountRepository
                .findById(id).orElseThrow(()->
                        new ResourceNotFoundException(
                                "Discount not found with ID: " + id)
                );
        discountRepository.delete(discount);
    }

    private DiscountStatus getDiscountStatus(Discounts discount){

        LocalDateTime now = LocalDateTime.now();

        if(discount.getStatus() == DiscountStatus.INACTIVE){
            return DiscountStatus.INACTIVE;
        }
        if(now.isAfter(discount.getEndDate())){
            return DiscountStatus.EXPIRED;
        }
        if (now.isBefore(discount.getStartDate())){
            return DiscountStatus.SCHEDULED;
        }
        return DiscountStatus.ACTIVE;
    }

    private void validateDiscount(DiscountRequestDTO requestDTO){

        if(requestDTO.getStartDate() == null){
            throw new BadRequestException("Start date is required");
        }
        if(requestDTO.getEndDate() == null){
            throw new BadRequestException("End date is required");
        }

        if(!requestDTO.getEndDate().isAfter(requestDTO.getStartDate())){
            throw new BadRequestException("End date must be after start date");
        }

        if(requestDTO.getDiscountType() == DiscountType.PERCENTAGE){
            if(requestDTO.getDiscountValue().compareTo(
                    new java.math.BigDecimal("100")
            ) > 0){
                throw  new BadRequestException("Percentage discount cannot exceed 100");
            }
        }
    }
}
