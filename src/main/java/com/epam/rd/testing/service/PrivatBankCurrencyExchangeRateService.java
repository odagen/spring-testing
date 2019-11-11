package com.epam.rd.testing.service;

import com.epam.rd.testing.repository.enums.Currency;
import com.epam.rd.testing.service.dto.CurrencyExchangeRateResponse;
import com.epam.rd.testing.service.dto.ExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class PrivatBankCurrencyExchangeRateService implements CurrencyExchangeRateService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.YYYY");
    private static final String RESPONSE_FORMAT = "json";
    private static final String EXCHANGE_RATE_PATH = "/p24api/exchange_rates";

    private final String baseUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public PrivatBankCurrencyExchangeRateService(@Value("${service.currency.baseUrl}") String baseUrl,
                                                 RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<ExchangeRate> tryGetCurrencyExchangeRate(Currency currency, LocalDate localDate) {
        return getCurrenciesExchangeRates(localDate).getCurrencyRates().stream()
                .filter(rate -> currency.name().equalsIgnoreCase(rate.getCurrency()))
                .findAny();
    }

    @Override
    public ExchangeRate getCurrencyExchangeRateFallBack(Currency currency) {
        //Some hardcoded fall-back logic
        return ExchangeRate.builder()
                .baseCurrency("UAH")
                .currency(currency.name())
                .purchaseRate(24.5)
                .saleRate(24.8)
                .build();
    }

    @Override
    public CurrencyExchangeRateResponse getCurrenciesExchangeRates(LocalDate localDate) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path(EXCHANGE_RATE_PATH)
                .query(RESPONSE_FORMAT)
                .queryParam("date", localDate.format(DATE_TIME_FORMATTER))
                .buildAndExpand(Collections.singletonMap("response_format", "json")).toUri();

        try {
            return restTemplate.getForObject(uri, CurrencyExchangeRateResponse.class);
        } catch (RuntimeException exc) {
            log.error("Currency rate cannot be retrieved", exc);

            return new CurrencyExchangeRateResponse();
        }
    }

}
