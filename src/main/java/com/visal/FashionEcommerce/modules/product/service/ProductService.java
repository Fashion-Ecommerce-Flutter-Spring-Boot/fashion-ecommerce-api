package com.visal.FashionEcommerce.modules.product.service;

import java.util.List;

public interface ProductService {


    void addProductImages(
            Long productId,
            List<Long> attachmentIds
    );
}
