package com.example.dummySbrigit.Filters;

import com.example.dummySbrigit.Entities.Uuid;
import com.example.dummySbrigit.Services.AdminService;
//import com.example.dummySbrigit.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AuthorizationFilter extends OncePerRequestFilter {
    private final AdminService adminService;

    public AuthorizationFilter(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//            if(request.getServletPath().equals("/abc")){
//                System.out.println("authorization called.");
//                  filterChain.doFilter(request, response);
//           }
//            else{
//                String AuthorizationHeader = request.getHeader("Authorization");
//                Uuid uuid= adminService.getToken(AuthorizationHeader.substring(7));
//                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(uuid.getEmail(), null, authorities);
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
            //}

    }
}
