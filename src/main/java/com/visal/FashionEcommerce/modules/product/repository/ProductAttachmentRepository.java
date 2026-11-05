package com.visal.FashionEcommerce.modules.product.repository;

import com.visal.FashionEcommerce.modules.product.model.ProductAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAttachmentRepository extends JpaRepository<ProductAttachment, Long > {
    boolean existsByAttachmentId(Long attachmentId);

    List<ProductAttachment> findByProductIdOrderByDisplayOrderAsc(Long productId);

    Optional<ProductAttachment> findByProductIdAndId(
            Long productId,
            Long id
    );

    void deleteByProductId(Long productId);

}
