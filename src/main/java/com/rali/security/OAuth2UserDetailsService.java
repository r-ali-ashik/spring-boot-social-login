//package com.rali.security;
//
//
//import com.rali.entity.User;
//import com.rali.repository.UserRepository;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Service
//public class OAuth2UserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public OAuth2UserDetailsService(@Lazy UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with : " + username));
//        return OAuth2UserPrincipal.create(user);
//    }
//
//    @Transactional
//    public UserDetails loadUserById(String id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
//        return OAuth2UserPrincipal.create(user);
//    }
//}