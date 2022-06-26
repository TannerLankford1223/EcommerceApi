package com.example.ecommerce.infrastructure.adapters;

import com.example.ecommerce.domain.ports.spi.ProductPersistencePort;
import com.example.ecommerce.infrastructure.entity.Product;
import com.example.ecommerce.infrastructure.persistence.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductRepoAdapter implements ProductPersistencePort {

    private final ProductRepository productRepo;

    public ProductRepoAdapter(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Page<Product> findByCategoryId(Long id, Pageable pageable) {
        return productRepo.findByCategoryId(id, pageable);
    }

    @Override
    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        return productRepo.findByNameContaining(name, pageable);
    }
}
