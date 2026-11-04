package com.visal.FashionEcommerce.modules.attachment.dto;


import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponseDTO {

    private Long id;

    private String originalFileName;

    private String mimeType;

    private Long fileSize;

    private String url;

    private String publicId;

    private String resourceType;

    private LocalDateTime createAt;

}
