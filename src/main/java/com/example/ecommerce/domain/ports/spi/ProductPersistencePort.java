package com.example.ecommerce.domain.ports.spi;

import com.example.ecommerce.infrastructure.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductPersistencePort {

    Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable);
    Page<Product> findByNameContaining(@RequestParam("name") String name, Pageable pageable);
}
