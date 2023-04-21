package com.example.dummySbrigit.Configuration;

import com.example.dummySbrigit.Filters.AuthenticationFilter;
import com.example.dummySbrigit.Filters.AuthorizationFilter;
import com.example.dummySbrigit.Services.AdminService;
//import com.example.dummySbrigit.Services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AdminService adminService;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(AdminService adminService, UserDetailsService userDetailsService) {
        this.adminService = adminService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter filter=new AuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),adminService);
        filter.setFilterProcessesUrl("/login");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.authorizeHttpRequests().requestMatchers("/login","/","/signupDriver","/abc").permitAll();
        //http.authorizeHttpRequests().requestMatchers("/create/user").hasRole("ADMIN");//.hasAuthority("ADMIN");
        //http.authorizeHttpRequests().requestMatchers("/add/product").hasRole("ADMIN");
        //http.authorizeHttpRequests().requestMatchers("/accounts/facebook/login/callback").authenticated().and().oauth2Login();
        //http.authorizeHttpRequests().requestMatchers("/go/auth").authenticated().and().oauth2Login().
        // successHandler(oauthSuccesssHandler);
        //http.authorizeHttpRequests().requestMatchers("/create/user").permitAll();
        //http.authorizeHttpRequests().requestMatchers("/add/product").permitAll();
        //http.authorizeHttpRequests().requestMatchers("/hello").authenticated().and().oauth2Login().successHandler(oauthSuccesssHandler);
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilter(filter);
//        http.authorizeHttpRequests().anyRequest().authenticated();

        http.addFilterBefore(new AuthorizationFilter(adminService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

