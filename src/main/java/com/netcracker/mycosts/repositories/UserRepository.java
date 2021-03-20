package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
