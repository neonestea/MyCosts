package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<Cost, Integer> {
}