package com.visal.FashionEcommerce.modules.attachment.repository;

import com.visal.FashionEcommerce.modules.attachment.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByPublicId(String publicId);

    boolean existsById(Long id);




}
