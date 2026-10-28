package com.visal.FashionEcommerce.modules.role.config;

import com.visal.FashionEcommerce.modules.role.model.Role;
import com.visal.FashionEcommerce.modules.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = List.of(
                Role.builder()
                        .name("ADMIN")
                        .description("System Administrator with full access")
                        .build(),

                Role.builder()
                        .name("STAFF")
                        .description("Staff responsible for store operations")
                        .build(),

                Role.builder()
                        .name("CUSTOMER")
                        .description("Customer who can brows and purchase products")
                        .build()
        );

        for (Role role : roles){
            if(!roleRepository.existsByName(role.getName())){
                roleRepository.save(role);
            }
        }
    }
}
