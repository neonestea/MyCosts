package com.netcracker.mycosts.services;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.mycosts.entities.*;
import com.netcracker.mycosts.repositories.CostRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CostService {

    private CostRepository costRepository;
    private AccountService accountService;
    private MonthCostsService monthCostsService;

    @Autowired
    public void setCostRepository(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setMonthCostsService(MonthCostsService monthCostsService) {
        this.monthCostsService = monthCostsService;
    }

    public List<Cost> getAll(String userId) {
        return costRepository.findCostByUserId(userId);
    }

    public void deleteCostById(int id) {
        costRepository.deleteCostById(id);
    }

    public Cost getCostById(int id) {
        return costRepository.findById(id).get();
    }

    public List<Cost> findCostsByUser(User user) {
        return costRepository.findAllByUser(user);
    }

    public List<Cost> findCostsByUserAndCategory(User user, Category category) {
        return costRepository.findAllByUserAndCategory(user, category);
    }

    @SneakyThrows
    public void save(Cost cost) {
        Double exchangeToUsdRate = getExchangeToUsdRate(cost);
        cost.setAmountUSD(cost.getAmount() * exchangeToUsdRate);
        int accountId = cost.getAccount().getId();
        Account account = accountService.getAccountById(accountId);
        double newAmount = account.getAmount() - cost.getAmount();
        account.setAmount(newAmount);
        createOrUpdateMonthCosts(cost);
        accountService.save(account);
        costRepository.save(cost);
    }

    private Double getExchangeToUsdRate(Cost cost) throws JsonProcessingException {
        Double exchangeToUsdRate = 1.0;
        Currency currency = cost.getAccount().getCurrency();
        if (currency != Currency.USD) {
            String uri = "https://v6.exchangerate-api.com/v6/502d6864b20b7f5b793bede4/latest/" + currency.name();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            ObjectMapper mapper = new ObjectMapper();
            //TODO check if result is error
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode rate = root.path("conversion_rates");
            exchangeToUsdRate = rate.path("USD").asDouble();
        }
        return exchangeToUsdRate;
    }

    public void deleteAllWithCreationDateTimeBefore(LocalDate minusMonths) {
        costRepository.deleteAllWithCreationDateTimeBefore(minusMonths);
    }

    private void createOrUpdateMonthCosts(Cost cost) {
        User user = cost.getUser();
        LocalDate costDate = cost.getDate();
        Account account = cost.getAccount();
        Category category = cost.getCategory();
        double amount = cost.getAmount();
        double amountUSD = cost.getAmountUSD();

        LocalDate startDate = LocalDate.of(costDate.getYear(), costDate.getMonth(), 1);
        MonthCosts monthCost = monthCostsService.findMonthCostsByUserAndAccountAndCategoryAndStartDate(user, account, category,
                startDate);

        if (monthCost == null) {
            monthCost = MonthCosts.builder()
                    .user(user)
                    .account(account)
                    .startDate(startDate)
                    .category(category)
                    .amount(amount)
                    .amountUSD(amountUSD)
                    .build();
        } else {
            monthCost.addCostAmount(amount);
            monthCost.addCostAmountUSD(amountUSD);
        }

        monthCostsService.save(monthCost);
    }
}
