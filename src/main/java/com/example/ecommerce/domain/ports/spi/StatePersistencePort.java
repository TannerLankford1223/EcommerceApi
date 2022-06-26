package com.example.ecommerce.domain.ports.spi;

import com.example.ecommerce.infrastructure.entity.State;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatePersistencePort {

    List<State> findByCountryCode(@Param("code") String code);
}
