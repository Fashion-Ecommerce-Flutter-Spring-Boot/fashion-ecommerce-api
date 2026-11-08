package com.visal.FashionEcommerce.modules.product.dto.request;

import com.visal.FashionEcommerce.modules.product.enums.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description name is required")
    private String description;

    //@NotNull(message = "Product category is required")
    private Long category;

    private Long brand;

    private Long discount;

    private ProductStatus status;

}
