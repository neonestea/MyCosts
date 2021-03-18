package com.netcracker.mycosts.services;

import java.util.List;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.repositories.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CostService {

    protected CostRepository costRepository;


    @Autowired
    public void setCostRepository(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    public List<Cost> getAll() {
        return costRepository.findAll();
    }

    public Cost addCost(Cost cost) {
        if(cost != null) {
            costRepository.save(cost);
            return cost;
        }
        return null;
    }

    public Cost getById(int id) {
        if(id > 0) {
            return costRepository.findById(id).get();
        }
        return null;
    }

    public void deleteById(int id) {
        if(id > 0) {
            costRepository.deleteById(id);
        }
    }
}
