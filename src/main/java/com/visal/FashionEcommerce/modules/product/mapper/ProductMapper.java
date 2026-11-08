package com.visal.FashionEcommerce.modules.product.mapper;

import com.visal.FashionEcommerce.common.exception.ResourceNotFoundException;
import com.visal.FashionEcommerce.modules.attachment.mapper.AttachmentMapper;
import com.visal.FashionEcommerce.modules.brand.dto.response.BrandSummary;
import com.visal.FashionEcommerce.modules.brand.model.Brand;
import com.visal.FashionEcommerce.modules.brand.repository.BrandRepository;
import com.visal.FashionEcommerce.modules.category.dto.response.CategorySummary;
import com.visal.FashionEcommerce.modules.category.model.Category;
import com.visal.FashionEcommerce.modules.category.repository.CategoryRepository;
import com.visal.FashionEcommerce.modules.discount.dto.response.DiscountSummary;
import com.visal.FashionEcommerce.modules.discount.model.Discount;
import com.visal.FashionEcommerce.modules.discount.repository.DiscountRepository;
import com.visal.FashionEcommerce.modules.product.dto.request.ProductRequestDTO;
import com.visal.FashionEcommerce.modules.product.dto.response.ProductAttachmentResponseDTO;
import com.visal.FashionEcommerce.modules.product.dto.response.ProductResponseDTO;
import com.visal.FashionEcommerce.modules.product.model.Product;
import com.visal.FashionEcommerce.modules.product.model.ProductAttachment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final DiscountRepository discountRepository;
    private final AttachmentMapper attachmentMapper;

    public Product toEntity(ProductRequestDTO requestDTO){
        Product product = new Product();
        Category category = categoryRepository.findById(requestDTO.getCategory())
                .orElseThrow(()->
                        new ResourceNotFoundException("Category not found with ID: "+ requestDTO.getCategory())
                );
        if(requestDTO.getBrand() != null){
            Brand brand = brandRepository.findById(requestDTO.getBrand())
                    .orElseThrow(()->
                            new ResourceNotFoundException("Brand not found with ID: "+ requestDTO.getBrand())
                    );
            product.setBrand(brand);
        }

        if(requestDTO.getDiscount() != null){
            Discount discount = discountRepository.findById(requestDTO.getDiscount())
                    .orElseThrow(()->
                            new ResourceNotFoundException("Discount not found with ID: " + requestDTO.getDiscount())
                    );
            product.setDiscount(discount);
        }

        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setCategory(category);

        return product;
    }

    public ProductResponseDTO toResponse(Product product){
        CategorySummary categorySummary = new CategorySummary();
        BrandSummary brandSummary = new BrandSummary();
        DiscountSummary discountSummary = new DiscountSummary();

        categorySummary.setId(product.getCategory() != null ?
                product.getCategory().getId(): null
        );
        categorySummary.setName(
                product.getCategory() != null ?
                        product.getCategory().getName(): null
        );

        brandSummary.setId(
                product.getBrand() != null ?
                product.getBrand().getId() : null
        );
        brandSummary.setName(
                product.getBrand() != null ?
                product.getBrand().getName() : null
        );

        discountSummary.setId(
                product.getDiscount() != null ?
                product.getDiscount().getId(): null
        );
        discountSummary.setName(
                product.getDiscount() != null ?
                product.getDiscount().getName() : null
        );
        discountSummary.setDiscountType(
                product.getDiscount() != null ?
                product.getDiscount().getDiscountType() : null
        );
        discountSummary.setDiscountValue(
                product.getDiscount() != null ?
                product.getDiscount().getDiscountValue() : null
        );
        discountSummary.setStartDate(
                product.getDiscount() != null ?
                product.getDiscount().getStartDate(): null
        );
        discountSummary.setEndDate(
                product.getDiscount() != null ?
                product.getDiscount().getEndDate() : null
        );

        return  ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(
                    categorySummary
                )
                .brand(
                        brandSummary
                )
                .discount(
                        discountSummary
                )
                .status(product.getStatus())
                .images(product.getAttachments()
                        .stream()
                        .sorted(Comparator.comparing(
                                ProductAttachment::getDisplayOrder
                        ))
                        .map(this::toProductAttachmentResponse)
                        .toList())
                .createAt(product.getCreateAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public ProductAttachmentResponseDTO toProductAttachmentResponse(
            ProductAttachment productAttachment
    ) {
        return ProductAttachmentResponseDTO.builder()
                .id(productAttachment.getId())
                .attachment(
                        attachmentMapper.toResponse(
                                productAttachment.getAttachment()
                        )
                )
                .isPrimary(productAttachment.getIsPrimary())
                .displayOrder(productAttachment.getDisplayOrder())
                .build();
    }
}
