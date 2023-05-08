package com.example.dummySbrigit.Filters;

//import com.example.dummySbrigit.Entities.Admin;
//import com.example.dummySbrigit.Entities.Uuid;
import com.example.dummySbrigit.Entities.User;
import com.example.dummySbrigit.Entities.UserRole;
import com.example.dummySbrigit.Entities.Uuid;
import com.example.dummySbrigit.Services.AdminService;
//import com.example.dummySbrigit.Services.UserService;
import com.example.dummySbrigit.Services.DriversService;
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
import java.util.List;
import java.util.UUID;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public final AuthenticationManager authenticationManager;
    public final AdminService adminService;
    public final DriversService driversService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, AdminService adminService,DriversService driversService) {
        this.authenticationManager = authenticationManager;
        this.adminService = adminService;
        this.driversService=driversService;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("attempt auth");
        System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("password"));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password"));
        System.out.println(usernamePasswordAuthenticationToken + "///");

//        String email = request.getParameter("username");
//        User user= driversService.getUserByEmail(email);
//        List<UserRole> listUserRole=user.getUserRoles();
//        System.out.println("\u001B[31m"+listUserRole.get(0).getRoleId().getRoleName().toString()+"\u001B[0m");

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
        String roleName= request.getParameter("role");
        System.out.println("User Authenticated Successfully!!!");
        String email = request.getParameter("username");
        uuidEntity.setEmail(email);
        User user= driversService.getUserByEmail(email);
        List<UserRole> listUserRole=user.getUserRoles();
        System.out.println("\u001B[31m"+listUserRole.get(0).getRoleId().getRoleName()+"\u001B[0m");
        adminService.CreateToken(uuidEntity);
        String redirectUrl="";
        if(roleName.equals("ADMIN")){
            redirectUrl = "/dashboard";
        }
        else if(roleName.equals("DRIVER")){
           redirectUrl= "/driver-profile";
        }
        else if(roleName.equals("RIDER")){
            redirectUrl="/rider-profile";
        }
        new DefaultRedirectStrategy().sendRedirect(request,response,redirectUrl);




         new ObjectMapper().writeValue(response.getOutputStream(),uuid.toString());
         //adminService.saveAdmin(new Admin());
    }
}

