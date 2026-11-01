package org.tardi.gateway.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tardi.gateway.client.OpenExchangeRatesClient;
import org.tardi.gateway.model.OerResponse;
import org.tardi.gateway.model.records.CurrencyResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class CurrencyService {

    @Inject
    @RestClient
    OpenExchangeRatesClient client;

    @ConfigProperty(name = "openexchangerates.app.id")
    String appId;

    public CurrencyResponse getYesterdayRate(String targetCurrency) {
        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

        OerResponse oer = client.getHistorical(yesterday, appId, targetCurrency);

        Double rate = oer.rates.get(targetCurrency);

        return new CurrencyResponse(targetCurrency, rate, oer.timestamp, yesterday);
    }

    public CurrencyResponse getRate(String targetCurrency, String date) {
        String targetDate = (date != null) ? date : LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        // Вчерашняя логика:
        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

        // Если date == null, значит просят сегодняшний (latest), иначе - исторический
        OerResponse oer;
        if (date == null) {
            oer = client.getLatest(appId, targetCurrency); // Сегодня
        } else {
            oer = client.getHistorical(date, appId, targetCurrency); // История
        }

        return new CurrencyResponse(targetCurrency, oer.rates.get(targetCurrency), oer.timestamp, date);
    }
}