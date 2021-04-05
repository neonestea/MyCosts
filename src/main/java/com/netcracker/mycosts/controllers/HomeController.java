package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.AccountService;
import com.netcracker.mycosts.services.CategoryService;
import com.netcracker.mycosts.services.CostService;
import com.netcracker.mycosts.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;

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
            data.put("accounts", user.getAccounts());
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
            data.put("accounts", user.getAccounts());
            List<Currency> currencies = Arrays.asList(Currency.values());
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
