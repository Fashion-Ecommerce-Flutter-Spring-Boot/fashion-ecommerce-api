package com.visal.FashionEcommerce.modules.product.service.impl;

import com.visal.FashionEcommerce.common.exception.BadRequestException;
import com.visal.FashionEcommerce.common.exception.ResourceNotFoundException;
import com.visal.FashionEcommerce.modules.attachment.dto.AttachmentResponseDTO;
import com.visal.FashionEcommerce.modules.attachment.model.Attachment;
import com.visal.FashionEcommerce.modules.attachment.repository.AttachmentRepository;
import com.visal.FashionEcommerce.modules.attachment.service.AttachmentService;
import com.visal.FashionEcommerce.modules.brand.model.Brand;
import com.visal.FashionEcommerce.modules.brand.repository.BrandRepository;
import com.visal.FashionEcommerce.modules.category.model.Category;
import com.visal.FashionEcommerce.modules.category.repository.CategoryRepository;
import com.visal.FashionEcommerce.modules.discount.model.Discount;
import com.visal.FashionEcommerce.modules.discount.repository.DiscountRepository;
import com.visal.FashionEcommerce.modules.product.dto.request.ProductRequestDTO;
import com.visal.FashionEcommerce.modules.product.dto.response.ProductResponseDTO;
import com.visal.FashionEcommerce.modules.product.enums.ProductStatus;
import com.visal.FashionEcommerce.modules.product.mapper.ProductMapper;
import com.visal.FashionEcommerce.modules.product.model.Product;
import com.visal.FashionEcommerce.modules.product.model.ProductAttachment;
import com.visal.FashionEcommerce.modules.product.repository.ProductAttachmentRepository;
import com.visal.FashionEcommerce.modules.product.repository.ProductRepository;
import com.visal.FashionEcommerce.modules.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final DiscountRepository discountRepository;
    private final ProductMapper productMapper;
    private final AttachmentService attachmentService;
    private final AttachmentRepository attachmentRepository;
    private final ProductAttachmentRepository productAttachmentRepository;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO, List<MultipartFile> images) {

        if (requestDTO.getName() == null || requestDTO.getName().isBlank()){
            throw  new BadRequestException("Product name cannot be empty");
        }
        if (requestDTO.getDescription() == null ||
                requestDTO.getDescription().isBlank()){
            throw  new BadRequestException("Product description cannot be empty");
        }
        if(requestDTO.getCategory() == null){
            throw  new BadRequestException("Product category is required");
        }

        Product product = productMapper.toEntity(requestDTO);
        Product savedProduct = productRepository.save(product);

        if(images != null && !images.isEmpty()){
            for(int i = 0; i < images.size(); i++){
                MultipartFile image = images.get(i);
                if (image == null || image.isEmpty()) {
                    throw new BadRequestException("Image cannot be empty");
                }
                // upload to cloudinary and save att
                AttachmentResponseDTO response = attachmentService.upload(image);

                // find saved att
                Attachment attachment = attachmentRepository.findById(response.getId()).orElseThrow(
                        ()->
                                new ResourceNotFoundException("Attachment not found with ID: " +response.getId())
                );
                // create relationship
                ProductAttachment productAttachment = new ProductAttachment();
                productAttachment.setProduct(savedProduct);
                productAttachment.setAttachment(attachment);
                productAttachment.setIsPrimary(i == 0);
                productAttachment.setDisplayOrder(i);
                productAttachmentRepository.save(productAttachment);
            }
        }

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Product not found with ID: "+ id)
                        );

        //ProductResponseDTO response = productMapper.toResponse(product);

        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toResponse);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product product = new Product();
        Category category = categoryRepository.findById(
                requestDTO.getCategory()
        ).orElseThrow(()->
                new ResourceNotFoundException("Category not found with ID: "+  requestDTO.getCategory())
        );

        Brand brand = brandRepository.findById(
                requestDTO.getBrand()
        ).orElseThrow(()->
                new ResourceNotFoundException("Brand not found with ID: " + requestDTO.getBrand())
                );

        Discount discount = discountRepository.findById(
                requestDTO.getBrand()
        ).orElseThrow(()->
                new ResourceNotFoundException("Discount not found with ID: " + requestDTO.getBrand())
        );

        product.setName(
                requestDTO.getName()
        );
        product.setDescription(
                requestDTO.getDescription()
        );
        product.setCategory(
                category
        );
        product.setBrand(
               brand
        );
        product.setDiscount(
               discount
        );
//        product.setAttachments(
//                requestDTO.
//        );
        product.setStatus(
                requestDTO.getStatus()
        );

        Product savedProduct = productRepository.save(product);
        //ProductResponseDTO response = productMapper.toResponse(savedProduct);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Product not found by ID: " + id)
        );
        productRepository.delete(product);
    }

    @Override
    public Page<ProductResponseDTO> searchProducts(String keyword, Pageable pageable) {

        Page<Product> products = productRepository.searchProducts(
                keyword, pageable
        );
        return products.map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponseDTO> filterProducts(
            String keyword,
            ProductStatus status,
            Long categoryId, Long brandId,
            Long discountId,
            Pageable pageable
    ) {
        Specification<Product> specification =
                Specification.where(
                        (root,
                         query,
                         cb)
                                -> cb.conjunction()
        );
        if(keyword != null && !keyword.trim().isBlank()){
            specification = specification.and(
                    (root, query, cb)
                            -> cb.like(
                                    cb.lower(root.get("name")),
                            "%" + keyword.trim().toLowerCase() + "%"
                    )
            );
        }

        if(status != null){
            specification = specification.and(
                    (root,
                     query,
                     cb)-> cb.equal(
                             root.get("status"), status
                    )
            );
        }
        if(categoryId != null){
            specification = specification.and(
                    (root,
                     query,
                     cb
                     )-> cb.equal(
                             root.get("category").get("id"), categoryId
                    )
            );
        }
        if(brandId != null){
            specification = specification.and(
                    (root,
                     query,
                     cb)-> cb.equal(
                                    root.get("brand").get("id"), brandId
                            )
            );
        }
        if(discountId != null){
            specification = specification.and(
                    (root,
                     query,
                     cb)-> cb.equal(
                             root.get("discount").get("id"), discountId
                    )
            );
        }

        Page<Product> products = productRepository.findAll(specification, pageable);

        return products.map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponseDTO> filterProductByStatus(ProductStatus status, Pageable pageable) {
        Page<Product> products = productRepository.findByStatus(
                status,
                pageable
        );
        return products.map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponseDTO> filterProductByCategory(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
        return products.map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponseDTO> filterProductByBrand(Long brandId, Pageable pageable) {
        Page<Product> products = productRepository.findByBrandId(brandId, pageable);
        return products.map(productMapper::toResponse);
    }
}
