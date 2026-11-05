package com.visal.FashionEcommerce.modules.product.model;

import com.visal.FashionEcommerce.modules.brand.model.Brand;
import com.visal.FashionEcommerce.modules.category.model.Category;
import com.visal.FashionEcommerce.modules.discount.model.Discounts;
import com.visal.FashionEcommerce.modules.product.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Product name is required")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discounts discount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private ProductStatus status = ProductStatus.DRAFT;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
            // orphanRemoval = true so removing a
            // ProductAttachment from the collection
            // deletes its relationship record
    )
    @OrderBy("displayOrder ASC")
    private List<ProductAttachment> attachments = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

}
