package com.visal.FashionEcommerce.modules.attachment.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.visal.FashionEcommerce.common.exception.BadRequestException;
import com.visal.FashionEcommerce.common.exception.ResourceNotFoundException;
import com.visal.FashionEcommerce.modules.attachment.dto.AttachmentResponseDTO;
import com.visal.FashionEcommerce.modules.attachment.mapper.AttachmentMapper;
import com.visal.FashionEcommerce.modules.attachment.model.Attachment;
import com.visal.FashionEcommerce.modules.attachment.repository.AttachmentRepository;
import com.visal.FashionEcommerce.modules.attachment.service.AttachmentService;
import com.visal.FashionEcommerce.modules.attachment.validator.AttachmentValidator;
import com.visal.FashionEcommerce.modules.product.model.ProductAttachment;
import com.visal.FashionEcommerce.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final Cloudinary cloudinary;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;
    private final AttachmentValidator attachmentValidator;
    private final UserRepository userRepository;
    private final ProductAttachment productAttachment;

    @Override
    public AttachmentResponseDTO upload(MultipartFile file) {
        attachmentValidator.validate(file);
        Map<String, Object> uploadResult;

        if (file.isEmpty() || file == null) {
            throw new BadRequestException("Please select a attachment file");
        }

        try {

            uploadResult =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", "fashion-ecommerce/attachments",
                                    "resource_type", "image"
                            )
                    );

        } catch (IOException e) {
            throw new RuntimeException("Fail to upload image to Cloudinary ");
        }

        Attachment attachment = new Attachment();

        attachment.setOriginalFileName(
                file.getOriginalFilename()
        );
        attachment.setMimeType(
                file.getContentType()
        );
        attachment.setFileSize(file.getSize());
        attachment.setUrl(
                uploadResult.get("secure_url").toString()
        );
        attachment.setPublicId(
                uploadResult.get("public_id").toString()
        );
        attachment.setResourceType(
                uploadResult.get("resource_type").toString()
        );

        try {

            Attachment savedAttachment = attachmentRepository.save(attachment);

            return attachmentMapper.toDTO(savedAttachment);

        } catch (Exception databaseException) {
            try {

            } catch (Exception cleanupException) {
                databaseException.addSuppressed(cleanupException);
            }
            throw  new RuntimeException(
                    "Failed to save attachment metadata",
                    databaseException
            );
        }

    }

    @Override
    public void deleteAttachment(Long id) {
        Attachment attachment = attachmentRepository
                .findById(id).orElseThrow(()->
                        new ResourceNotFoundException("Attachment not found with ID: "+ id)
                );
        // check first before delete
        if(userRepository.existsByProfileImage(id)
        || userRepository.existsByProfileImage(id)){
            throw new BadRequestException("Attachment is currently being used");
        }

        try {
            cloudinary.uploader().destroy(
                    attachment.getPublicId(),
                    ObjectUtils.asMap(
                            "resource_type", attachment.getResourceType()
                    )
            );
                attachmentRepository.delete(attachment);
        } catch (Exception e) {
            throw new RuntimeException("Fail to delete attachment");
        }
    }
}
