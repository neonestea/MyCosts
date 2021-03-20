package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.entities.UserCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {

    List<UserCategory> findCategoriesByUserId(int userId);

}
