package com.netcracker.mycosts.services;

import java.util.Set;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    protected CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /*public Set<Category> getAll(int userId) {
        return categoryRepository.;
    }*/

    public Category findCategoryByNameHash(int nameHash) {
        return categoryRepository.findCategoryByNameHash(nameHash);
    }

    public void save(Category userCategory) {
         categoryRepository.save(userCategory);
    }

}
