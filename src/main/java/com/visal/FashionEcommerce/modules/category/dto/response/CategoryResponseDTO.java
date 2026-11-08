package com.visal.FashionEcommerce.modules.category.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {

    private Long id;

    private Long parentId;

    private String parentName;

    private String name;

    private String description;

    private boolean status;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;
}
