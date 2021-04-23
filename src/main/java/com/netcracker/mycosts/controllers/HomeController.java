package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${spring.profiles.active}")
    private String profile;

    private CategoryService categoryService;
    private UserService userService;
    private CostService costService;
    private RegularCostService regularCostService;
    private AccountService accountService;

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("profile", user);
        }
        else {
            data.put("profile", null);
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }

    @GetMapping("/stat")
    public String statistics(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("profile", user);
        }
        else {
            data.put("profile", null);
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "stat";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        user = userService.getUserById(user.getId());
        if(user != null) {
            data.put("costs", costService.getAll(user.getId()));
            data.put("profile", user);
            Set<Account> accounts = new HashSet<>();
            List<Account> accountsFromDb = accountService.getAllUserAccounts(user.getId());
            for(Account acc : accountsFromDb){
                if (acc.getActive() == true){
                    accounts.add(acc);
                }
            }
            data.put("accounts", accounts);
            data.put("categories", user.getCategories());
            model.addAttribute("frontendData", data);
            model.addAttribute("isDevMode", "dev".equals(profile));

            return "costs";
        }
        else {
            return "redirect:/login";
        }
    }

    @GetMapping("/regular")
    public String regularCosts(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("regularCosts", regularCostService.getAll(user.getId()));
            data.put("profile", user);
            Set<Account> accounts = new HashSet<>();
            List<Account> accountsFromDb = accountService.getAllUserAccounts(user.getId());
            for(Account acc : accountsFromDb){
                if (acc.getActive() == true){
                    accounts.add(acc);
                }
            }
            data.put("accounts", accounts);
            data.put("categories", user.getCategories());
            model.addAttribute("frontendData", data);
            model.addAttribute("isDevMode", "dev".equals(profile));

            return "regular";
        }
        else {
            return "redirect:/login";
        }
    }

    @GetMapping("/categories")
    public String categories(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        user = userService.getUserById(user.getId());
        if(user != null){
            data.put("profile", user);
            data.put("categories", user.getCategories());
            model.addAttribute("frontendData", data);
            model.addAttribute("isDevMode", "dev".equals(profile));

            return "categories";
        }
        else {
            return "redirect:/login";
        }
    }
    @GetMapping("/accounts")
    public String accounts(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("profile", user);
            List<Account> accountsFromDb = accountService.getAllUserAccounts(user.getId());

            List<Currency> currencies = Arrays.asList(Currency.values());
            Set<Account> accounts = new HashSet<>();
            for(Account acc : accountsFromDb){
                if (acc.getActive() == true){
                    accounts.add(acc);
                }
            }
            data.put("accounts", accounts);
            data.put("currencies", currencies);
            model.addAttribute("frontendData", data);
            model.addAttribute("isDevMode", "dev".equals(profile));

            return "accounts";
        }
        else {
            return "redirect:/login";
        }
    }

    //TODO add view with month costs

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }@Autowired
    public void setRegularCostService(RegularCostService regularCostService) {
        this.regularCostService = regularCostService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

}
