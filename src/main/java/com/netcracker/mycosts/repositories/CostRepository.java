package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface CostRepository extends JpaRepository<Cost, Integer> {

    List<Cost> findCostByUserId(String userId);
    void deleteCostById(int id);

    List<Cost> findCostByUserIdAndAccount(String userId, Account account);

    @Query("delete from Cost c where c.date <= :costDate")
    @Modifying
    void deleteAllWithCreationDateTimeBefore(@Param("costDate") LocalDate costDate);

    List<Cost> findAllByUser(User user);

    List<Cost> findAllByUserAndCategory(User user, Category category);
}