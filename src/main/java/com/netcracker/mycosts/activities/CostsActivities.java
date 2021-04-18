package com.netcracker.mycosts.activities;

import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.entities.RegularCost;
import com.netcracker.mycosts.repositories.RegularCostRepository;
import com.netcracker.mycosts.services.CostService;

import java.time.temporal.ChronoUnit;
import java.util.List;

import com.netcracker.mycosts.services.RegularCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
//TODO MAKE CRON
public class CostsActivities {

    public static final int MONTHS_TO_STORE_COSTS = 1;

    private CostService costService;
    private RegularCostService regularCostService;

    /*@Scheduled(fixedRate = 10000000)
    public void deleteOldCost() {
        costService.deleteAllWithCreationDateTimeBefore(LocalDate.now().minusMonths(MONTHS_TO_STORE_COSTS));
    }*/

    //TODO get regular costs by parts
    //TODO save costs
    //TODO refactor this. Please!!!
   /* @Scheduled(fixedRate = 10000000)
    public void regularCosts() {
        List<RegularCost> regularCosts = regularCostService.findAll();
        regularCosts.forEach(regularCost -> {
                    LocalDate now = LocalDate.now();
                    if (regularCost.isEveryMonth()) {
                        if (ChronoUnit.MONTHS.between(regularCost.getLastDate(), now) == 1) {
                            updateLastDateOfRegularCost(regularCost, now);
                        }
                    } else if (ChronoUnit.DAYS.between(regularCost.getLastDate(), now) == regularCost.getPeriod()) {
                        updateLastDateOfRegularCost(regularCost, now);
                    }
                }
        );
    }*/

    private void updateLastDateOfRegularCost(RegularCost regularCost, LocalDate now) {
        makeCost(regularCost);
        regularCost.setLastDate(now);
        regularCostService.save(regularCost);
    }

    private void makeCost(RegularCost regularCost) {
        //TODO make conventor from regular cost to cost, but late
        Cost cost = Cost.builder()
                .user(regularCost.getUser())
                .amount(regularCost.getAmount())
                .date(LocalDate.now())
                .account(regularCost.getAccount())
                .category(regularCost.getCategory())
                .build();
        costService.save(cost);
    }


    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    @Autowired
    public void setRegularCostService(RegularCostService regularCostService) {
        this.regularCostService = regularCostService;
    }
}
