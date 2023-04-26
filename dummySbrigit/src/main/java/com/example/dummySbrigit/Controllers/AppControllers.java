package com.example.dummySbrigit.Controllers;

import com.example.dummySbrigit.Entities.Admin;
import com.example.dummySbrigit.Entities.Drivers;
import com.example.dummySbrigit.Services.AdminService;
import com.example.dummySbrigit.Services.DriversService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("https://c93e-112-196-113-2.ngrok-free.app")
@RestController
public class AppControllers {
    @Autowired
    AdminService adminService;
    @Autowired
    DriversService driversService;
    @CrossOrigin("https://c93e-112-196-113-2.ngrok-free.app")
    @GetMapping("/hello")
    public String getMsg(){
        System.out.println("hello!!");
        return "hi";
    }
    @PostMapping("/addDriver")
    public String Register(HttpServletRequest request) {
        String fName = request.getParameter("fname");
        String lName = request.getParameter("lname");
        String email = request.getParameter("email");
        String phNo =  request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("cnfPassword");
        String city = request.getParameter("city");
        String inviteCode = request.getParameter("inviteCode");
//        if(!confirmPassword.equals(password)) {
//            System.out.println("//////////////////" + "inside");
//            return "error";
//        }
        Drivers user = Drivers.builder()
                .firstName(fName)
                .lastName(lName)
                .email(email)
                .phone(phNo)
                .password(password)
                .city(city)
                .build();
        System.out.println("////////////" + user);
        driversService.saveUser(user);
        driversService.register(user);
        return "driver created";
    }
    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam int otp) {
        boolean isVerified = driversService.verify(email, otp);
        System.out.println("verification");
        if (isVerified) {
            return ResponseEntity.ok("OTP verification successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }
//    @GetMapping("/")
//    public String listObjects(Model model) {
//        List<Drivers> drivers = driversService.allDrivers();
//        System.out.println(drivers);
//        //model.addAttribute("objects", objects);
//        return "u";
//    }
    @PostMapping("/updateDriver")
    public String updateDriverProfile(HttpServletRequest request){
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        //String country = request.getParameter("country");
        //String imgUrl = request.getParameter("imgUrl");
//        if(!confirmPassword.equals(password)) {
//            System.out.println("//////////////////" + "inside");
//            return "error";
//        }
        System.out.println("inside////////////" + firstName + lastName + email + phone);
        Drivers admin= driversService.UpdateDriversInUser(firstName,lastName,email,phone);
        System.out.println(admin);

        return "updateDriver";
    }
    @PatchMapping("/update/driver")
    public String updateDriver(HttpServletRequest request){
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        driversService.UpdateDriversInUser(firstName,lastName,email,phone);
        return "User Updated!!!";
    }



}
