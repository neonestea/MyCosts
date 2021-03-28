package com.netcracker.mycosts.services;

import java.util.List;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private Category findCategoryByNameHash(int nameHash) {
        return categoryRepository.findCategoryByNameHash(nameHash);
    }

    public Category findCategoryByName(String categoryName) {
        Category candidateCategory = findCategoryByNameHash(categoryName.hashCode());
        if (candidateCategory.getName().toLowerCase().equals(categoryName.toLowerCase())) {
            return candidateCategory;
        }
        return categoryRepository.findCategoryByName(categoryName);
    }

    @Transactional
    public List<Category> findDefaultCategories() {
        return categoryRepository.findCategoriesByIsDefault(true);
    }

    public void save(Category userCategory) {
        userCategory.setNameHash(userCategory.getName().hashCode());
        categoryRepository.save(userCategory);
    }

}
