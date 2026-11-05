package com.visal.FashionEcommerce.modules.discount.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visal.FashionEcommerce.modules.discount.enums.DiscountStatus;
import com.visal.FashionEcommerce.modules.discount.enums.DiscountType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRequestDTO {

    @NotBlank(message = "Discount name is required")
    private String name;

    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.01", message = "Discount value must be greater than 0")
    @Digits(integer = 8, fraction = 2,
            message = "Discount value must have up to 8 integer digits and 2 decimal places")
    private BigDecimal discountValue;

    @NotNull(message = "Discount status is required")
    private DiscountStatus status;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start cannot be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

}
