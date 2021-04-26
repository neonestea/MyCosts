package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.User;
import java.util.List;

//import com.netcracker.mycosts.entities.UserEmailView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findUserByName(String name);

    User findUserByEmail(String email);

    User findUserById(String id);


    //List<UserEmailView> findBy();
}
