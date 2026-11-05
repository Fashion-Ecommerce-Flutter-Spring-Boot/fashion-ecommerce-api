package com.visal.FashionEcommerce.modules.attachment.controller;

import com.visal.FashionEcommerce.common.response.ApiResponse;
import com.visal.FashionEcommerce.common.response.ApiResponseUtil;
import com.visal.FashionEcommerce.modules.attachment.dto.AttachmentResponseDTO;
import com.visal.FashionEcommerce.modules.attachment.service.AttachmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
@Tag(name = "Attachments API")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<AttachmentResponseDTO>> upload(
            @RequestParam("file") MultipartFile file
            ){
        AttachmentResponseDTO upload = attachmentService.upload(file);

        ApiResponse<AttachmentResponseDTO> response = ApiResponseUtil.success(
                "attachment uploaded successfully",
                upload
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("deleteDiscount/{id}")
    public ResponseEntity<ApiResponse<AttachmentResponseDTO>>  deleteAttachment(
        @PathVariable Long id
    ){
        attachmentService.deleteAttachment(id);

        ApiResponse<AttachmentResponseDTO> response = ApiResponseUtil.success(
                "Attachment deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

}
