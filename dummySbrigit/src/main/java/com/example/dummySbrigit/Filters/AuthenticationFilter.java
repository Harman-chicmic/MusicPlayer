package com.example.dummySbrigit.Filters;

import com.example.dummySbrigit.Entities.Admin;
import com.example.dummySbrigit.Entities.Uuid;
import com.example.dummySbrigit.Services.AdminService;
//import com.example.dummySbrigit.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.UUID;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public final AuthenticationManager authenticationManager;
    public final AdminService adminService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.adminService = adminService;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("attempt auth");
        System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("password"));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password"));
        System.out.println(usernamePasswordAuthenticationToken + "///");
        // return usernamePasswordAuthenticationToken;
        //authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //.authenticate(new UsernamePasswordAuthenticationToken(username, password))`
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UUID uuid=UUID.randomUUID();
        Uuid uuidEntity = new Uuid();
        uuidEntity.setUuid(uuid.toString());
        System.out.println(authResult+"hey");

        System.out.println("User Authenticated Successfully!!!");
        String email = request.getParameter("username");
        uuidEntity.setEmail(email);


        adminService.CreateToken(uuidEntity);
        String redirectUrl = "http://localhost:8081/dashboard";
//        response.addHeader("Authorization","Subham Kumar");

        new DefaultRedirectStrategy().sendRedirect(request,response,redirectUrl);




         new ObjectMapper().writeValue(response.getOutputStream(),uuid.toString());
         //adminService.saveAdmin(new Admin());
    }
}

