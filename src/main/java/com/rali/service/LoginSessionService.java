package com.rali.service;

import com.rali.dto.LoginSession;

public interface LoginSessionService {
    LoginSession createOrUpdateLoginSession(String userId, String token, String refreshToken);

    LoginSession getExistingSession(String userId);

    boolean isValidSession(String userId, String token);

     void disableActiveSessions(String userId);
}