package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.*;
import com.netcracker.mycosts.services.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;
    private CostService costService;
    private MonthCostsService monthCostsService;
    private RegularCostService regularCostService;

    @PostMapping("/category")
    public ResponseEntity<Category> create(@RequestBody Category category, @AuthenticationPrincipal User user) {
        category = categoryService.save(category);
        user = userService.getUserById(user.getId());
        boolean alreadyExist = user.getCategories().stream()
                .map(cat -> cat.getName())
                .collect(Collectors.toSet())
                .contains(category.getName());
        if (!alreadyExist) {
            user.addCategory(category);
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/category/{id}")
    public void delete(@PathVariable int id, @AuthenticationPrincipal User user) {
        System.out.println("ID was: " + id);
        Category category = categoryService.findCategoryById(id);
        userService.removeCategoryFromUser(user.getId(), category.getId());
        if (shouldDeleteCategory(category)) {
            final Category otherCategory = categoryService.findCategoryByName("Other");
            List<Cost> costs = costService.findCostsByUserAndCategory(user, category);
            List<MonthCosts> monthCosts = monthCostsService.findMonthCostsByUserAndCategory(user, category);
            List<RegularCost> regularCosts = regularCostService.findRegularCostsByUserAndCategory(user, category);
            costs.forEach(cost -> cost.setCategory(otherCategory));
            monthCosts.forEach(cost -> cost.setCategory(otherCategory));
            regularCosts.forEach(cost -> cost.setCategory(otherCategory));
            categoryService.deleteCategoryById(category.getId());
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> update(@RequestParam String newCategoryName,
                                           @PathVariable int id, @AuthenticationPrincipal User user) {
        Category category = categoryService.findCategoryById(id);
        user = userService.getUserById(user.getId());
        if (category.getUsers().size() == 1 && !category.isDefault()) {
            category.setName(newCategoryName);
            category.setNameHash(newCategoryName.hashCode());
            category = categoryService.save(category);
        } else {
            user.removeCategory(category);
            category = Category.builder()
                    .isDefault(false)
                    .name(newCategoryName)
                    .nameHash(newCategoryName.hashCode())
                    .build();
            user.addCategory(category);
            category = categoryService.save(category);
            Category finalCategory = category;
            costService.findCostsByUser(user).forEach(cost -> cost.setCategory(finalCategory));
            regularCostService.findRegularCostsByUser(user).forEach(cost -> cost.setCategory(finalCategory));
            monthCostsService.findMonthCostsByUser(user).forEach(cost -> cost.setCategory(finalCategory));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(category);
    }

    private boolean shouldDeleteCategory(Category category) {
        return category.getUsers().size() == 0 && !category.isDefault();
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
    public void setMonthCostsService(MonthCostsService monthCostsService) {
        this.monthCostsService = monthCostsService;
    }

    @Autowired
    public void setRegularCostService(RegularCostService regularCostService) {
        this.regularCostService = regularCostService;
    }
}

