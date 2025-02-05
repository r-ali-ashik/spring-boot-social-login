package com.rali.service.impl;

import com.rali.dto.LoginSession;
import com.rali.exception.ApiException;
import com.rali.service.LoginSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static com.rali.util.AppUtils.isNotNull;
import static com.rali.util.AppUtils.isNull;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginSessionServiceImpl implements LoginSessionService {

    private final ApplicationContext applicationContext;

    @Override
    @CachePut(value = "loginSession", key = "#userId")
    public LoginSession createOrUpdateLoginSession(String userId, String token, String refreshToken) {
        try {
            log.info("create or update login session");
            LoginSessionService loginSessionService = applicationContext.getBean(LoginSessionService.class);
            LoginSession loginSession = loginSessionService.getExistingSession(userId);
            if (isNull(loginSession) || !loginSession.isActive()) {
                loginSession = new LoginSession();
            }
            loginSession.setUserId(userId);
            loginSession.setAccessToken(token);
            loginSession.setRefreshToken(refreshToken);
            loginSession.setActive(true);
            loginSession.setUpdatedAt(OffsetDateTime.now());
            return loginSession;
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException(ex);
        }
    }

    @Override
    @Cacheable(value = "loginSession", key = "#userId", unless = "true")
    public LoginSession getExistingSession(String userId) {
        return null;
    }

    @Override
    public boolean isValidSession(String userId, String token) {
        try {
//            LoginSessionService loginSessionService = applicationContext.getBean(LoginSessionService.class);
//            LoginSession loginSession = loginSessionService.getExistingSession(userId);
//            return isNotNull(loginSession) && (token.equals(loginSession.getAccessToken()) || token.equals(loginSession.getRefreshToken()) && loginSession.isActive());
            return true;
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException(ex);
        }
    }

    @Override
    @CacheEvict(value = "loginSession", key = "#userId")
    public void disableActiveSessions(String userId) {
        try {
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException(ex);
        }
    }
}
