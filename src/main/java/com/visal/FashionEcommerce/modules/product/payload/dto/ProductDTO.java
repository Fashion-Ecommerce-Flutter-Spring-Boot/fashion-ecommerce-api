package com.visal.FashionEcommerce.modules.product.payload.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long categoryId;

    private Long brandId;

    //private com.visal.FashionEcommerce.discount.model.Discount discount;

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private com.visal.FashionEcommerce.product.enums.ProductStatus status;

}
