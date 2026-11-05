package com.visal.FashionEcommerce.modules.discount.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visal.FashionEcommerce.modules.discount.enums.DiscountStatus;
import com.visal.FashionEcommerce.modules.discount.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Product name is required")
    private String name;

    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @Column(name = "start_date", nullable = false)
//    @JsonFormat(pattern = "dd/mm/yyyy")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
//    @JsonFormat(pattern = "dd/mm/yyyy")
    private LocalDateTime endDate;

    private DiscountStatus status;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
