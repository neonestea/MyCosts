package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    //Set<Category> findCategoriesByUserId(int userId);

    Category findCategoryByNameHash(int nameHash);

}
