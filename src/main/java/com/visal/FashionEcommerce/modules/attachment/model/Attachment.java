package com.visal.FashionEcommerce.modules.attachment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(nullable = false)
    private String url;

    @Column(name = "public_id", nullable = false, unique = true)
    private String publicId;

    @Column(name = "resource_type")
    private String resourceType;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

}
