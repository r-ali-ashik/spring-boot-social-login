package com.rali.service.impl;

import com.rali.dto.LoginSessionDto;
import com.rali.entity.User;
import com.rali.exception.ApiException;
import com.rali.repository.UserRepository;
import com.rali.security.OAuth2TokenService;
import com.rali.service.AuthService;
import com.rali.service.LoginSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String ISSUER = "https://www.myawesomedomain.co.uk/";

    private final OAuth2TokenService oAuth2TokenService;
    private final LoginSessionService loginSessionService;
    private final UserRepository userRepository;


    @Override
    public LoginSessionDto createOrUpdateLoginSession(String username) {

        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            String token = oAuth2TokenService.createToken(user, ISSUER);
            String refreshToken = oAuth2TokenService.createRefreshToken(user, ISSUER);
            //LoginSession loginSession = loginSessionService.createOrUpdateLoginSession(userId., token, refreshToken);
            return LoginSessionDto.builder()
                    .token(token)
                    .refreshToken(token)
                    .build();

        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException(ex);
        }
    }
}
