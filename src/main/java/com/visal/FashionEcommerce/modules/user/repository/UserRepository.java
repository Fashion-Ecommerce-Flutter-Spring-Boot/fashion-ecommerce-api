package com.visal.FashionEcommerce.modules.user.repository;

import com.visal.FashionEcommerce.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByProfileImageId(Long attachmentId);
}
