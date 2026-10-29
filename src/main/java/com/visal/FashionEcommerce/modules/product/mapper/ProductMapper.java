package com.visal.FashionEcommerce.modules.product.mapper;

import com.visal.FashionEcommerce.modules.brand.model.Brand;
import com.visal.FashionEcommerce.modules.product.model.Product;
import com.visal.FashionEcommerce.modules.product.payload.dto.ProductDTO;

public class ProductMapper {

    ProductDTO toDto(Product product){
        if(product == null) return null;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setStatus(product.getStatus());
        productDTO.setDescription(product.getDescription());

        Brand brand = new Brand();

        return productDTO;
    }


}
