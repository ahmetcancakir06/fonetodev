package com.example.fonet_mulakat.service;

import com.example.fonet_mulakat.dto.CustomUserDetails;
import com.example.fonet_mulakat.model.User;
import com.example.fonet_mulakat.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);
        if(user ==null) {
            throw new UsernameNotFoundException("Kullanıcı Bulunamadı");
        }
        return new CustomUserDetails(user);
    }
}
