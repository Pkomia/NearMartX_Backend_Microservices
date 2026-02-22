package com._Rbrothers.user_service.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private int status;
    private String error;
    private LocalDateTime timestamp;
}
