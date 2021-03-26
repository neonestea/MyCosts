
package com.netcracker.mycosts;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.repositories.AccountRepository;
import com.netcracker.mycosts.repositories.CategoryRepository;
import com.netcracker.mycosts.repositories.UserRepository;
import com.netcracker.mycosts.services.AccountService;
import com.netcracker.mycosts.services.CostService;
import com.netcracker.mycosts.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyCostsApplicationTests {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private AccountRepository accRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accService;

    @Autowired
    private CostService costService;

    @Test
    void contextLoads() {
        User user = User.builder()
                .name("oleg")
                .email("sad")
                .build();

        userService.save(user);

        User user2 = User.builder()
                .name("nastya")
                .email("SAd")
                .build();

        userService.save(user2);


        Category category1 = Category.builder()
                .name("pivo")
                .build();

        category1.addUser(user);

        Category category2 = Category.builder()
                .name("chips")
                .build();

        category2.addUser(user2);
        category2.addUser(user);


        categoryRepo.save(category1);
        categoryRepo.save(category2);

/*
        Account acc2 = Account.builder()
                .user(user)
                .name("asasdasd")
                .currency("dsadsad")
                .build();
        Account acc1 = Account.builder()
                .amount(200)
                .currency("asd")
                .name("asd")
                .build();
        acc1.setUser(user2);
        accService.create(acc1);
        accService.create(acc2);

        Cost cost2 = Cost.builder()
                .user(user)
                .account(acc2)
                .build();
        Cost cost1 = Cost.builder()
                .user(user)
                .account(acc1)
                .build();

        costService.create(cost1);
        costService.create(cost2);*/
    }

}

