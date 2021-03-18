package com.netcracker.mycosts.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.CostService;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.entities.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

@RestController
public class CostController {

    protected CostService costService;

    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    /*@GetMapping("/{user}/costs")
    List<Cost> allUserCosts(@PathVariable User user) {
        return costService.getAll(user);
    }

    @PostMapping("/{user}/costs")
    Cost newCost(@RequestBody Cost newCost, User user) {

        return costService.addCost(newCost);
    }

    @DeleteMapping("/{user}/costs/{id}")
    void deleteCost(@PathVariable User user, @PathVariable int id) {
        costService.deleteById(user, id);
    }*/
}
