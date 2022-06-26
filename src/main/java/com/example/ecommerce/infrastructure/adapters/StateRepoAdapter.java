package com.example.ecommerce.infrastructure.adapters;

import com.example.ecommerce.domain.ports.spi.StatePersistencePort;
import com.example.ecommerce.infrastructure.entity.State;
import com.example.ecommerce.infrastructure.persistence.StateRepository;

import java.util.List;

public class StateRepoAdapter implements StatePersistencePort {

    private final StateRepository stateRepo;

    public StateRepoAdapter(StateRepository stateRepo) {
        this.stateRepo = stateRepo;
    }

    @Override
    public List<State> findByCountryCode(String code) {
        return stateRepo.findByCountryCode(code);
    }
}
