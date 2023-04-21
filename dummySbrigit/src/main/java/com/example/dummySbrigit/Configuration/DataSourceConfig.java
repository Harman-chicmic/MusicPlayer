package com.example.dummySbrigit.Configuration;

import com.example.dummySbrigit.Entities.Admin;
import com.example.dummySbrigit.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Autowired
    AdminService adminService;
    @Bean
    public void createAdmin(){
        Admin admin=adminService.getUserByEmail("harman@gmail.com");
        if(admin==null){
            admin=Admin.builder().country("India").email("harman@gmail.com").phone("0987654321").firstName("Harman").lastName("Admin").password("Harman@chicmic").imgUrl("assets/img/admin.jpg").build();
            adminService.saveAdmin(admin);
        }
    }
}