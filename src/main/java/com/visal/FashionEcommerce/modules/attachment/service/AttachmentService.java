package com.visal.FashionEcommerce.modules.attachment.service;

import com.visal.FashionEcommerce.modules.attachment.dto.AttachmentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentResponseDTO upload(MultipartFile file);

    void deleteAttachment(Long id);
}


