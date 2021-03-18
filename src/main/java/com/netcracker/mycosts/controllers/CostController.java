package com.netcracker.mycosts.controllers;

import java.util.List;
//import com.netcracker.mycosts.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.CostService;
import com.netcracker.mycosts.entities.Cost;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CostController {

    protected CostService costService;

    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    @GetMapping("/costs")
    List<Cost> all() {
        return costService.getAll();
    }

    @PostMapping("/costs")
    Cost newCost(@RequestBody Cost newCost) {
        return costService.addCost(newCost);
    }

    @GetMapping("/costs/{id}")
    Cost one(@PathVariable int id) {
        return costService.getById(id);
    }

    @DeleteMapping("/costs/{id}")
    void deleteCost(@PathVariable int id) {
        costService.deleteById(id);
    }
}
