package org.tardi.gateway.model.records;

public record CurrencyResponse(
        String targetCurrency,
        Double rate,
        Long timestamp,
        String date


) {
    @Override
    public String toString() {
        return "CurrencyResponse{" +
                "targetCurrency='" + targetCurrency + '\'' +
                ", rate=" + rate +
                ", timestamp=" + timestamp +
                ", date='" + date + '\'' +
                '}';
    }
}