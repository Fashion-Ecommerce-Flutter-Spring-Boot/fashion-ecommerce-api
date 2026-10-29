package com.visal.FashionEcommerce.modules.brand.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandResponseDTO {

    private Long id;

    private String name;

    private String description;

//    private  logoAttachmentId;

    private boolean status;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
