package com.vm.service;

import com.vm.entity.VMUser;
import com.vm.repository.VMUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    VMUserRepository vmUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        VMUser vmUser = vmUserRepository.findByEmailAddress(username);
        if (vmUser == null) {
            throw new UsernameNotFoundException("VMUser not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(vmUser.getEmailAddress(), vmUser.getPassword(),
                new ArrayList<>());
    }
}
