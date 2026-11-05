package com.visal.FashionEcommerce.modules.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    private String name;

    private String description;

    private Long category;

    private Long brand;

    private Long discount;

}
