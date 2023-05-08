package com.example.dummySbrigit.Filters;

import com.example.dummySbrigit.Entities.Uuid;
import com.example.dummySbrigit.Services.AdminService;
//import com.example.dummySbrigit.Services.UserService;
import com.example.dummySbrigit.Services.DriversService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    private final AdminService adminService;
    private final DriversService driversService;

    public AuthorizationFilter(AdminService adminService,DriversService driversService) {
        this.adminService = adminService;
        this.driversService=driversService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
log.info("\u001B[31m"+request.getServletPath()+"\u001B[0m");
//            if(request.getServletPath().equals("/abc")||request.getServletPath().equals("/abcd")){
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
                return;
            //}

    }
}
