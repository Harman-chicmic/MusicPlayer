package com.example.dummySbrigit.Services;

import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminService adminService;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AdminService adminService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminService = adminService;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("iiiii");
        com.example.dummySbrigit.Entities.Admin admin = adminService.getAdmin(email);
        if (email==null || admin==null) {
            throw new UsernameNotFoundException("Username not found");
        }
        System.out.println(admin+"///");
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        return new User
                (admin.getEmail(),admin.getPassword(), authorities);
    }
}
