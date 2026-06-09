package com.company.simpleservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        int status,
        String message,
        List<String> errors,
        LocalDateTime timestamp
) {}
