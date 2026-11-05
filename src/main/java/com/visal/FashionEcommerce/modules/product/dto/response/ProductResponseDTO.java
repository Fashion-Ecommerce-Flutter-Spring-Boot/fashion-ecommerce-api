package com.visal.FashionEcommerce.modules.product.dto.response;


import com.visal.FashionEcommerce.modules.product.model.ProductAttachment;
import com.visal.FashionEcommerce.modules.product.enums.ProductStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;

    private String name;

    private String description;

    private Long category;

    private Long brand;

    private Long discount;

    private ProductStatus status;

    private List<ProductAttachment> attachments = new ArrayList<>();

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
