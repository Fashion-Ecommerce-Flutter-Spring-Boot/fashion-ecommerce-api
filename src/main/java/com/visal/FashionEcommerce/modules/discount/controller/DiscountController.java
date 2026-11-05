package com.visal.FashionEcommerce.modules.discount.controller;


import com.visal.FashionEcommerce.common.response.ApiResponse;
import com.visal.FashionEcommerce.common.response.ApiResponseUtil;
import com.visal.FashionEcommerce.modules.discount.dto.request.DiscountRequestDTO;
import com.visal.FashionEcommerce.modules.discount.dto.response.DiscountResponseDTO;
import com.visal.FashionEcommerce.modules.discount.service.DiscountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
@Tag(name = "Discounts API")
public class DiscountController {

    private  final DiscountService discountService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DiscountResponseDTO>> createDiscount(
           @Valid @RequestBody DiscountRequestDTO responseDTO
    ){
        DiscountResponseDTO discount = discountService.createDiscount(responseDTO);

        ApiResponse<DiscountResponseDTO> response = ApiResponseUtil.success(
          "Discount created successfully",
                    discount
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DiscountResponseDTO>>> getAllDiscounts(
            Pageable pageable
    ){
        Page<DiscountResponseDTO> discounts = discountService.getAllDiscounts(pageable);
        ApiResponse<Page<DiscountResponseDTO>> response = ApiResponseUtil.success(
                "Discounts retrieve successfully",
                discounts
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountResponseDTO>> getDiscountById(
           @PathVariable Long id
    ){
        DiscountResponseDTO discount = discountService.getDiscountById(id);

        ApiResponse<DiscountResponseDTO> response = ApiResponseUtil.success(
                "Discount retrieve successfully",
                discount
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DiscountResponseDTO>> updateDiscount(
            @PathVariable Long id ,
            @Valid @RequestBody  DiscountRequestDTO requestDTO
    ){
        DiscountResponseDTO discount = discountService.updateDiscount(
                id, requestDTO
        );

        ApiResponse<DiscountResponseDTO> response = ApiResponseUtil.success(
                "Discount updated successfully",
                discount
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDiscount(
            @PathVariable Long id
    ){
        discountService.deleteDiscount(id);
        ApiResponse<Void> response = ApiResponseUtil.success(
                "Discount delete successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

}
