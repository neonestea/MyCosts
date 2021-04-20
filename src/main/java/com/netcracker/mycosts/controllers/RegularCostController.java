package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.RegularCost;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.RegularCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegularCostController {

    private RegularCostService regularCostService;

    @PostMapping("/regular_costs")
    public ResponseEntity<RegularCost> addRegularCost(@RequestBody RegularCost regularCost,
                                                      @AuthenticationPrincipal User user) {
        regularCost.setUser(user);
        regularCostService.save(regularCost);
        return ResponseEntity.status(HttpStatus.CREATED).body(regularCost);
    }

    @DeleteMapping("/regular_costs")
    public ResponseEntity<RegularCost> deleteRegularCost(@RequestParam int regularCostId,
                                  @AuthenticationPrincipal User user) {
        regularCostService.deleteById(regularCostId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Autowired
    public void setRegularCostService(RegularCostService regularCostService) {
        this.regularCostService = regularCostService;
    }
}
