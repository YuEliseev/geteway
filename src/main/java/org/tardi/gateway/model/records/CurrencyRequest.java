package org.tardi.gateway.model.records;

import jakarta.annotation.Nullable;

public record CurrencyRequest(String targetCurrency, @Nullable String date) {}
