package com.netcracker.mycosts.services;

import java.time.LocalDate;
import java.util.List;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.repositories.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CostService {

    private CostRepository costRepository;

    @Autowired
    public void setCostRepository(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    public List<Cost> getAll(String userId) {
        return costRepository.findCostByUserId(userId);
    }

    public void deleteCostById(int id) {
        costRepository.deleteCostById(id);
    }

    public Cost getCostById(int id) {
        return costRepository.findById(id).get();
    }

    public void save(Cost cost) {
         costRepository.save(cost);
    }

    public void deleteAllWithCreationDateTimeBefore(LocalDate minusMonths) {
        costRepository.deleteAllWithCreationDateTimeBefore(minusMonths);
    }
}
