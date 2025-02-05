package com.rali.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class LoginSession {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private boolean isActive = true;
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt;
}
