package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.RegularCost;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.repositories.RegularCostRepository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class RegularCostService {

    private RegularCostRepository regularCostRepository;

    @Autowired
    public void setRegularCostRepository(RegularCostRepository regularCostRepository) {
        this.regularCostRepository = regularCostRepository;
    }

    public List<RegularCost> getAll(String userId) {
        return regularCostRepository.findRegularCostByUserId(userId);
    }

    public void save(RegularCost regularCost) {

        regularCostRepository.save(regularCost);
    }

    public void deleteById(int regularCostId) {
        regularCostRepository.deleteById(regularCostId);
    }

    public void regularCostProcedure() {
        regularCostRepository.regularCostProcedure();
    }

   public List<RegularCost> findRegularCostsByUser(User user) {
        return regularCostRepository.findAllByUser(user);
    }

    public List<RegularCost> findRegularCostsByUserAndCategory(User user, Category category) {
        return regularCostRepository.findRegularCostsByUserAndCategory(user, category);
    }
}
