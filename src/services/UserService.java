package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    protected UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
