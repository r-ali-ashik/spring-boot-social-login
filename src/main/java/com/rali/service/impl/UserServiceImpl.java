package com.rali.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rali.entity.Role;
import com.rali.entity.User;
import com.rali.exception.ApiException;
import com.rali.repository.UserRepository;
import com.rali.security.oauth2.user.OAuth2UserInfo;
import com.rali.security.oauth2.user.OAuth2UserInfoFactory;
import com.rali.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Collections;

import static com.rali.util.AppUtils.isNull;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void handleOidcUser(String provider, OidcUser oidcUser) {
        try {
            OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oidcUser.getAttributes());
            this.saveOrUpdateOAuth2User(provider, oAuth2UserInfo);
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException(ex);
        }
    }

    @Override
    @Transactional
    public void handleOAuth2User(String provider, OAuth2User oAuth2User) {
        try {
            OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());
            this.saveOrUpdateOAuth2User(provider, oAuth2UserInfo);

        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException(ex);
        }
    }

    private void saveOrUpdateOAuth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = userRepository.findByUsername(oAuth2UserInfo.getUsername())
                .orElse(null);
        if (isNull(user)) {
            user = new User();
            user.setEmail(oAuth2UserInfo.getEmail());
            user.setUsername(oAuth2UserInfo.getUsername());
            user.setProvider(provider);

            Role role = new Role();
            role.setId(1L);
            user.setRoles(Collections.singleton(role));

        }
        user.setFirstName(oAuth2UserInfo.getFirstName());
        user.setLastName(oAuth2UserInfo.getLastName());
        user.setImage(oAuth2UserInfo.getImageUrl());
        user.setUpdatedAt(OffsetDateTime.now());

        userRepository.save(user);
    }
}
