package com.visal.FashionEcommerce.product.repository;

import com.visal.FashionEcommerce.product.enums.ProductStatus;
import com.visal.FashionEcommerce.modules.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategory(Long categoryId, Pageable pageable);

    Page<Product> findByBrandId(Long brandId, Pageable pageable);

    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    Page<Product> findByCategoryIdAndStatus(
            Long categoryId,
            ProductStatus status,
            Pageable pageable
    );

    Page<Product> findByBrandIdAndStatus(
            Long BrandId,
            ProductStatus status,
            Pageable pageable
    );

    @Query("""
        SELECT p
        FROM Product p
        LEFT JOIN p.brand b
        LEFT JOIN p.category c
        WHERE
            LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);

}
