package com.visal.FashionEcommerce.modules.category.dto.request;


import com.visal.FashionEcommerce.modules.category.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    private Long parentId;
//    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Category name must not exceed 100 characters.")
    private String name;
    private String description;
}
