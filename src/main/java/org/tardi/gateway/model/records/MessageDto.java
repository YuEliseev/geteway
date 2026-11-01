package org.tardi.gateway.model.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record MessageDto(
        @JsonProperty("value")
        String value,
        @JsonProperty("code")
        String code,
        @JsonProperty("timestamp")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
        Instant timestamp
) {
    public MessageDto {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Value must not be null");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp must not be null");
        }
    }
}
