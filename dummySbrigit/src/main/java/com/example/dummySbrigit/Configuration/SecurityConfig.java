package com.example.dummySbrigit.Configuration;

import com.example.dummySbrigit.Filters.AuthenticationFilter;
import com.example.dummySbrigit.Filters.AuthorizationFilter;
import com.example.dummySbrigit.Services.AdminService;
//import com.example.dummySbrigit.Services.UserService;
import com.example.dummySbrigit.Services.DriversService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
@EnableAsync
@Order(2)
public class SecurityConfig {
    private final AdminService adminService;
    private final UserDetailsService userDetailsService;
    private final DriversService driversService;

    public SecurityConfig(AdminService adminService, UserDetailsService userDetailsService, DriversService driversService) {
        this.adminService = adminService;
        this.userDetailsService = userDetailsService;
        this.driversService=driversService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider1() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
        AuthenticationFilter filter1=new AuthenticationFilter(authenticationManager1(http.getSharedObject(AuthenticationConfiguration.class)),adminService,driversService);
        filter1.setFilterProcessesUrl("/login1");
        //filter1.setFilterProcessesUrl("/login2");
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
        http.addFilter(filter1);
//        http.authorizeHttpRequests().anyRequest().authenticated();

        http.addFilterBefore(new AuthorizationFilter(adminService,driversService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager1(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(30);
        executor.initialize();
        return executor;
    }
}

