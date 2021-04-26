package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.entities.UserEmailView;
import com.netcracker.mycosts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class UserService {

    private UserRepository userRepository;
    private CategoryService categoryService;

    public User getUserById(String id) {
        return userRepository.findUserById(id);
    }

    public User getUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User create(User user) {
        List<Category> defaultCategories = categoryService.findDefaultCategories();
        user.addCategories(defaultCategories);
        return save(user);
    }

    public void removeCategoryFromUser(String userId, int categoryId) {
        User user = userRepository.findUserById(userId);
        Category category = categoryService.findCategoryById(categoryId);
        user.removeCategory(category);
        save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
    
    public List<UserEmailView> getUsersEmails() {
       return userRepository.findBy();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
