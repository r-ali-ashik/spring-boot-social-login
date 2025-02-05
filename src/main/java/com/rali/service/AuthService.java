package com.rali.service;


import com.rali.dto.LoginSessionDto;
import com.rali.entity.User;
import com.rali.security.oauth2.user.OAuth2UserInfo;
import org.springframework.security.core.Authentication;

public interface AuthService {
    LoginSessionDto createOrUpdateLoginSession(String userId);
}
