package com.visal.FashionEcommerce.modules.product.dto.response;


import com.visal.FashionEcommerce.modules.brand.dto.response.BrandSummary;
import com.visal.FashionEcommerce.modules.category.dto.response.CategorySummary;
import com.visal.FashionEcommerce.modules.discount.dto.response.DiscountSummary;
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

    private CategorySummary category;

    private BrandSummary brand;

    private DiscountSummary discount;

//    private Long category;
//    private String categoryName;
//
//    private Long brand;
//    private String brandName;
//
//    private Long discount;
//    private String discountName;

    private ProductStatus status;

    private List<ProductAttachmentResponseDTO> images = new ArrayList<>();

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;
}
