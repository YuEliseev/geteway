package org.tardi.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OerResponse {
    public String base;
    public Map<String, Double> rates;
    public Long timestamp;
}