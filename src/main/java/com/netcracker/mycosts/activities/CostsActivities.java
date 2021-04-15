package com.netcracker.mycosts.activities;

import com.netcracker.mycosts.repositories.CostRepository;
import com.netcracker.mycosts.services.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class CostsActivities {

    public static final int MONTHS_TO_STORE_COSTS = 1;

    private CostService costService;

   /* @Scheduled(fixedRate = 60000)
    public void deleteOldCost() {
        costService.deleteAllWithCreationDateTimeBefore(LocalDate.now().minusMonths(MONTHS_TO_STORE_COSTS));
    }
*/
    @Autowired
    public void setcostService(CostService costService) {
        this.costService = costService;
    }
}
