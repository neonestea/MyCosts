package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface RegularCostRepository extends JpaRepository<RegularCost, Integer> {

    List<RegularCost> findRegularCostByUserId(String userId);
    void deleteRegularCostById(int id);
    List<RegularCost> findRegularCostByUserIdAndAccount(String userId, Account account);

    @Procedure(procedureName = "regular_cost_func")
    void regularCostProcedure();

    List<RegularCost> findAllByUser(User user);

    List<RegularCost> findRegularCostsByUserAndCategory(User user, Category category);
}