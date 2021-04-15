package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.RegularCost;
import com.netcracker.mycosts.repositories.RegularCostRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegularCostService {

    private RegularCostRepository regularCostRepository;

    @Autowired
    public void setRegularCostRepository(RegularCostRepository regularCostRepository) {
        this.regularCostRepository = regularCostRepository;
    }

    public List<RegularCost> findAll() {
        return regularCostRepository.findAll();
    }

    public void save(RegularCost regularCost) {
        regularCostRepository.save(regularCost);
    }
}
