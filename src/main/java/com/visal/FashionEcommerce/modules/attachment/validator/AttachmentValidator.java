package com.visal.FashionEcommerce.modules.attachment.validator;

import com.visal.FashionEcommerce.common.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
public class AttachmentValidator {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final Set<String> ALLOWED_MIME_TYPE = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    public void validate(MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new BadRequestException("File cannot be empty");
        }

        if(file.getSize() > MAX_FILE_SIZE){
            throw  new BadRequestException("File size must not exceed 5 MB");
        }

        String mimeType = file.getContentType();

        if(mimeType == null || !ALLOWED_MIME_TYPE.contains(mimeType.toLowerCase())){
            throw  new BadRequestException("Only JPG, PNG, and WebP image are allowed");
        }

        String fileName = file.getName();

        if(fileName == null || fileName.isBlank()){
            throw  new BadRequestException("Original file name is required");
        }
    }
}
