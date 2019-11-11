package com.epam.rd.testing.service;

import com.epam.rd.testing.repository.enums.Currency;
import com.epam.rd.testing.service.dto.CurrencyExchangeRateResponse;
import com.epam.rd.testing.service.dto.ExchangeRate;

import java.time.LocalDate;
import java.util.Optional;

public interface CurrencyExchangeRateService {

    CurrencyExchangeRateResponse getCurrenciesExchangeRates(LocalDate localDate);

    Optional<ExchangeRate> tryGetCurrencyExchangeRate(Currency currency, LocalDate localDate);

    ExchangeRate getCurrencyExchangeRateFallBack(Currency currency);
}
