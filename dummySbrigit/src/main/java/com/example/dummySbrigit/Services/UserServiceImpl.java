package com.example.dummySbrigit.Services;

import com.example.dummySbrigit.Entities.Admin;
import com.example.dummySbrigit.Entities.Drivers;
import jakarta.servlet.http.HttpServletRequest;
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

    public UserServiceImpl( AdminService adminService,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminService = adminService;
    }
    public String getCurrentUrl(HttpServletRequest request){
        String url = request.getServletPath();
        return url;
    }


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        System.out.println("iiiii");
//        com.example.dummySbrigit.Entities.Admin admin;
//        Drivers drivers;
//
//        if(getCurrentUrl().equals("/login1")){
//         admin = adminService.getAdmin(email);}
//        else {
//            drivers=dr
//        }
//        if (email==null || admin==null) {
//            throw new UsernameNotFoundException("Username not found");
//        }
//        System.out.println(admin+"///");
//        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
//        return new User
//                (admin.getEmail(),admin.getPassword(), authorities);
//    }
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
