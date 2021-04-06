package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.AccountService;
import com.netcracker.mycosts.services.CategoryService;
import com.netcracker.mycosts.services.CostService;
import com.netcracker.mycosts.services.UserService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private CategoryService categoryService;
    private UserService userService;
    private CostService costService;
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
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("costs", costService.getAll(user.getId()));
            data.put("profile", user);
            Set<Account> accounts = new HashSet<>();
            Set<Account> accountsFromDb = user.getAccounts();
            for(Account acc : accountsFromDb){
                if (acc.getActive() == true){
                    System.out.println(acc.getActive());
                    accounts.add(acc);
                }
            }
            data.put("accounts", accounts);
            data.put("categories", user.getCategories());
            model.addAttribute("frontendData", data);
            return "costs";
        }
        else {
            return "redirect:/login";
        }
    }

    @GetMapping("/categories")
    public String categories(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("profile", user);
            data.put("categories", user.getCategories());
            System.out.println("CATEGORIES " + user.getCategories());
            model.addAttribute("frontendData", data);
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
            List<Account> accountsFromDb = accountService.getAll(user.getId());

            List<Currency> currencies = Arrays.asList(Currency.values());
            Set<Account> accounts = new HashSet<>();
            for(Account acc : accountsFromDb){
                if (acc.getActive() == true){
                    System.out.println(acc.getActive());
                    accounts.add(acc);
                }
            }
            data.put("accounts", accounts);
            data.put("currencies", currencies);
            model.addAttribute("frontendData", data);
            return "accounts";
        }
        else {
            return "redirect:/login";
        }
    }

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
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
