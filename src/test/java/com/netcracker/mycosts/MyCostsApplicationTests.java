package com.netcracker.mycosts;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.entities.UserCategory;
import com.netcracker.mycosts.repositories.UserCategoryRepository;
import com.netcracker.mycosts.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

@SpringBootTest
class MyCostsApplicationTests {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserCategoryRepository categoryRepo;

    @Test
    void contextLoads() {
        User user = User.builder()
                .name("oleg")
                .passwordHash("asd")
                .build();

        userRepo.save(user);
        UserCategory category1 = UserCategory.builder()
                .name("pivo")
                .user(user)
                .build();

        UserCategory category2 = UserCategory.builder()
                .name("chips")
                .user(user)
                .build();


        categoryRepo.save(category1);
        categoryRepo.save(category2);



        System.out.println(categoryRepo.findByUser(user, Sort.by("user_id").descending()) + " епт");
    }

}
