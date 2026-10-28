package com.visal.FashionEcommerce.modules.product.payload.request;
import com.visal.FashionEcommerce.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchRequest {
    private String keyword;
    private Long categoryId;
    private Long brandId;
    private ProductStatus status;
}
