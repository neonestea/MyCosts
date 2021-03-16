package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.entities.UserCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {

    List<UserCategory> findByUser(User user);
    List<UserCategory> findByUser(User user, Sort sort);

    List<UserCategory> findByUserId(int userId);

    List<UserCategory> findByIdGreaterThanEqual(int id, Sort sort);

}
