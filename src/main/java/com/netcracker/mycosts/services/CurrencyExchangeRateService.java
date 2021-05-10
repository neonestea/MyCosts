package com.netcracker.mycosts.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.CurrencyExchangeRate;
import com.netcracker.mycosts.repositories.CurrencyExchangeRateRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CurrencyExchangeRateService {

    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Autowired
    public void setCurrencyExchangeRateRepository(CurrencyExchangeRateRepository currencyExchangeRateRepository) {
        this.currencyExchangeRateRepository = currencyExchangeRateRepository;
    }

    public void save(CurrencyExchangeRate currencyExchangeRate) {
        currencyExchangeRateRepository.save(currencyExchangeRate);
    }


    @SneakyThrows
    public void getAndSaveExchangeRates() {
        //TODO add condition if rates exists
        Double exchangeToUsdRate;
        LocalDate currentDate = LocalDate.now();
        Currency[] currencies = Currency.values();
        for (Currency currency : currencies) {
            String uri = "https://v6.exchangerate-api.com/v6/612198050b3168e80bedf8bb/latest/" + currency.name();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode rate = root.path("conversion_rates");
            exchangeToUsdRate = rate.path("USD").asDouble();
            CurrencyExchangeRate currencyExchangeRate = CurrencyExchangeRate.builder()
                    .date(currentDate)
                    .currency(currency)
                    .rate(exchangeToUsdRate)
                    .build();
            currencyExchangeRateRepository.save(currencyExchangeRate);
        }
    }

    public CurrencyExchangeRate findByCurrencyAndDate(Currency currency, LocalDate date) {
        List<CurrencyExchangeRate> distinctByCurrencyAndDate = currencyExchangeRateRepository
                .findByCurrencyAndDate(currency, date);

        if (distinctByCurrencyAndDate.size() == 0) {
            getAndSaveExchangeRates();
            distinctByCurrencyAndDate = currencyExchangeRateRepository
                    .findByCurrencyAndDate(currency, date);
        }
        CurrencyExchangeRate currencyExchangeRate = distinctByCurrencyAndDate.get(0);
        return currencyExchangeRate;
    }
}
