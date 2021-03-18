package com.netcracker.mycosts.services;

import java.util.List;
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

    /*public List<UserCategory> getAll() {
        return userCategoryRepository.findAll();
    }

    public UserCategory addUserCategory(UserCategory userCategory) {
        if(userCategory != null) {
            userCategoryRepository.save(userCategory);
            return userCategory;
        }
        return null;
    }

    public UserCategory getById(int id) {
        if(id > 0) {
            if (userCategoryRepository.findById(id).isPresent()){
                return userCategoryRepository.findById(id).get();
            }
        }
        return null;
    }

    public void deleteById(int id) {
        if(id > 0) {
            userCategoryRepository.deleteById(id);
        }
    }*/
}
