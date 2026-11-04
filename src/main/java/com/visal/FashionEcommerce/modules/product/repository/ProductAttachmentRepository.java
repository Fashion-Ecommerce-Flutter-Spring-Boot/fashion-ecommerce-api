package com.visal.FashionEcommerce.modules.product.repository;

import com.visal.FashionEcommerce.modules.attachment.model.Attachment;
import com.visal.FashionEcommerce.modules.product.model.ProductAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttachmentRepository extends JpaRepository<Long, ProductAttachment> {
    boolean existsByProductAttachmentId(Attachment attachment);

}
