package com.visal.FashionEcommerce.modules.brand.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequestDTO {

    @Size(max = 100, message = "Brand name must not exceed 100 characters")
    private String name;

    private String description;
}
