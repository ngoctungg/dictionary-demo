package com.pm.service;

import com.pm.entity.UserEntity;
import com.pm.model.CustomUserDetails;
import com.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByAccount(s);
        if(user == null){
            throw new UsernameNotFoundException(s);
        }

        return new CustomUserDetails(user);
    }
}
