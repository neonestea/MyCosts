package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    protected UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }
    public User create(User user) {
        return userRepository.save(user);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //при удалениии пользователя удаляем связанные с ним счета,
    //категории и траты
}
