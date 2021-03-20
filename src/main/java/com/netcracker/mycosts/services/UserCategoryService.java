package com.netcracker.mycosts.services;

import java.util.List;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.UserCategory;
import com.netcracker.mycosts.repositories.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCategoryService {

    protected UserCategoryRepository userCategoryRepository;


    @Autowired
    public void setUserCategoryRepository(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    public List<UserCategory> getAll(int userId) {
        return userCategoryRepository.findCategoriesByUserId(userId);
    }

    public UserCategory create(UserCategory userCategory) {
        return userCategoryRepository.save(userCategory);
    }

}
