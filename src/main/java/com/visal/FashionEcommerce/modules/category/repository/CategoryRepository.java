package com.visal.FashionEcommerce.modules.category.repository;

import com.visal.FashionEcommerce.modules.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    Boolean existsByName(String name);

    List<Category> findByParentIdIsNullAndStatusTrue();

    Page<Category> findByNameContainingIgnoreCaseAndStatusTrue(
            String name,
            Pageable pageable
    );

//    Page<Category> findByNameContainingIgnoreCase(
//            String name,
//            Pageable pageable
//    );



    /*

    List<Category> findByStatus(boolean status);

//    Page<Category> findByStatus(boolean status, Pageable pageable);

    List<Category> findByParentIdId(Long parentId);

    */




}
