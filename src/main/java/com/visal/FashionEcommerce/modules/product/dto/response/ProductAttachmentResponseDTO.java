package com.visal.FashionEcommerce.modules.product.dto.response;

import com.visal.FashionEcommerce.modules.attachment.dto.AttachmentResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductAttachmentResponseDTO {
    private Long id;

    private AttachmentResponseDTO attachment;

    private Boolean isPrimary;

    private Integer displayOrder;
}
