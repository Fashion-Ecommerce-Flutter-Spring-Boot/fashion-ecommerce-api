package com.visal.FashionEcommerce.modules.product.model;

import com.visal.FashionEcommerce.modules.attachment.model.Attachment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "product_id", "attachment_id"
                        }
                )
        }
)
@Getter
@Setter
public class ProductAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", nullable = false)
    private Attachment attachment;

    @Column(nullable = false)
    private Boolean isPrimary;

    @Column(nullable = false)
    private Integer displayOrder;
}
