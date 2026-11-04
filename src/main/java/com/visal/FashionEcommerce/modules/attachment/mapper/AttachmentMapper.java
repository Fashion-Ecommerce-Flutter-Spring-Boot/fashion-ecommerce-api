package com.visal.FashionEcommerce.modules.attachment.mapper;

import com.visal.FashionEcommerce.modules.attachment.dto.AttachmentResponseDTO;
import com.visal.FashionEcommerce.modules.attachment.model.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper {
    public AttachmentResponseDTO toDTO(Attachment attachment){
        return AttachmentResponseDTO.builder()
                .id(attachment.getId())
                .originalFileName(attachment.getOriginalFileName())
                .mimeType(attachment.getMimeType())
                .fileSize(attachment.getFileSize())
                .url(attachment.getUrl())
                .publicId(attachment.getPublicId())
                .resourceType(attachment.getResourceType())
                .createAt(attachment.getCreateAt())
                .build();
    }

}
