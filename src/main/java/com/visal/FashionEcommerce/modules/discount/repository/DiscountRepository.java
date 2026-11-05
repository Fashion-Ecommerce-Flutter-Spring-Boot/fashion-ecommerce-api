package com.visal.FashionEcommerce.modules.discount.repository;

import com.visal.FashionEcommerce.modules.discount.model.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discounts, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
