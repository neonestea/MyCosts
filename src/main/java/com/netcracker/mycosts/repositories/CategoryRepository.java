package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findCategoryByNameHash(int nameHash);

    Category findCategoryByName(String name);
}
