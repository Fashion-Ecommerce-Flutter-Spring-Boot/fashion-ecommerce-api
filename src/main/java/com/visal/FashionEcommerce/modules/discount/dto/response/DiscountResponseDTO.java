package com.visal.FashionEcommerce.modules.discount.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visal.FashionEcommerce.modules.discount.enums.DiscountStatus;
import com.visal.FashionEcommerce.modules.discount.enums.DiscountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountResponseDTO {

    private Long id;

    private String name;

    private DiscountType discountType;

    private BigDecimal discountValue;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private DiscountStatus status;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;
}
