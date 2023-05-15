package com.example.JobPortal.Configuration;

import com.example.JobPortal.CustomFilters.AuthenticationFilter;
import com.example.JobPortal.CustomFilters.AuthorizationFilter;
import com.example.JobPortal.Services.UsersService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UsersService userService;

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public SecurityConfiguration(UsersService userService, UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

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
        AuthenticationFilter filter=new AuthenticationFilter(userService, authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)));
        filter.setFilterProcessesUrl("/login");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.authorizeHttpRequests().requestMatchers("/login","/","/go/redirect","/hello").permitAll();
        http.authorizeHttpRequests().anyRequest().permitAll();
        //http.authorizeHttpRequests().requestMatchers("/create/user").hasRole("ADMIN");//.hasAuthority("ADMIN");
        //http.authorizeHttpRequests().requestMatchers("/add/product").hasRole("ADMIN");
        //http.authorizeHttpRequests().requestMatchers("/accounts/facebook/login/callback").authenticated().and().oauth2Login();
//        http.authorizeHttpRequests().anyRequest().permitAll();
        //http.authorizeHttpRequests().requestMatchers("/create/user").permitAll();
        //http.authorizeHttpRequests().requestMatchers("/add/product").permitAll();
        //http.authorizeHttpRequests().requestMatchers("/hello").authenticated().and().oauth2Login().successHandler(oauthSuccesssHandler);
        http.addFilter(filter);

        http.addFilterBefore(new AuthorizationFilter(userService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

