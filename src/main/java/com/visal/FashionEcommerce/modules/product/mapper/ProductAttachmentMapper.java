package com.visal.FashionEcommerce.modules.product.mapper;


import com.visal.FashionEcommerce.modules.attachment.mapper.AttachmentMapper;
import com.visal.FashionEcommerce.modules.product.dto.response.ProductAttachmentResponseDTO;
import com.visal.FashionEcommerce.modules.product.model.ProductAttachment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductAttachmentMapper {

    private final AttachmentMapper attachmentMapper;

    ProductAttachmentResponseDTO toDTO(
            ProductAttachment productAttachment
    ){
        return ProductAttachmentResponseDTO.builder()
                .id(productAttachment.getId())
                .attachment(
                        attachmentMapper.toDTO(
                                productAttachment.getAttachment()
                        )
                )
                .isPrimary(productAttachment.getIsPrimary())
                .displayOrder(productAttachment.getDisplayOrder())
                .build();
    }
}
