/*
package com.netcracker.mycosts;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Cost;
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

import java.util.List;

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
                .passwordHash("asd")
                .build();

        userService.create(user);

        User user2 = User.builder()
                .name("nastya")
                .passwordHash("asd")
                .build();

        userService.create(user2);
        */
/*UserCategory category1 = UserCategory.builder()
                .name("pivo")
                .user(user)
                .build();

        UserCategory category2 = UserCategory.builder()
                .name("chips")
                .user(user)
                .build();


        categoryRepo.save(category1);
        categoryRepo.save(category2);
*//*


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
        costService.create(cost2);

        List<Account> accountsByUserId = accService.getAll(user.getId());
        User foundUser = userService.getUserById(user.getId());
        System.out.println( "ACCOUNTS: " + accountsByUserId);
        System.out.println("FOUND USER: " + foundUser);
        //accRepo.delete(acc1);
        //accRepo.delete(acc2);
        //userService.deleteById(user.getId());
        accountsByUserId = accService.getAll(user.getId());
        System.out.println("ACCOUNTS: " + accountsByUserId);
        //accService.deleteOne(acc1);
        //accService.deleteAll(user.getId());
        //accountsByUserId = accService.getAll(user.getId());
        //System.out.println("ACCOUNTS: " + accountsByUserId);

        List<Cost> costsByUserId = costService.getAll(user.getId());
        List<Cost> costsByUserIdAndAccount = costService.getAll(user.getId(), acc1);
        System.out.println("ALL COSTS: " + costsByUserId);
        System.out.println("ACCOUNT COSTS: " + costsByUserIdAndAccount);
        //costService.deleteOne(cost1);
        List<Cost> costsByUserIdAndAccount1 = costService.getAll(user.getId(), acc1);
        System.out.println("ACCOUNT COSTS: " + costsByUserIdAndAccount1);
        List<Account> accountsByUserId2 = accService.getAll(user2.getId());
        System.out.println("ACCOUNTS2: " + accountsByUserId2);
    }

}
*/
