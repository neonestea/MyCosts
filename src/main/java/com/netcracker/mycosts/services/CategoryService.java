package com.netcracker.mycosts.services;

import java.util.List;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
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
        if (candidateCategory == null) {
            return null;
        } else if (candidateCategory.getName().toLowerCase().equals(categoryName.toLowerCase())) {
            return candidateCategory;
        }
        return categoryRepository.findCategoryByName(categoryName);
    }

    public List<Category> findDefaultCategories() {
        return categoryRepository.findCategoriesByIsDefault(true);
    }

    public Category save(Category userCategory) {
        Category candidateCategory = findCategoryByName(userCategory.getName());
        if (candidateCategory == null) {
            userCategory.setNameHash(userCategory.getName().hashCode());
            return categoryRepository.save(userCategory);
        }
        return candidateCategory;
    }

    public Category findCategoryById(int id) {
        return categoryRepository.findCategoryById(id);
    }

    public void deleteCategoryById(int id) {
        categoryRepository.deleteCategoryById(id);
    }

}
