package com.example.dummySbrigit.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PageControllers {
    @GetMapping("/driver-profile")
    public String driverProfile(){
        return "driver-profile";
    }
    @GetMapping("/rider-payment")
    public String riderPayment(){return "rider-payment";}
    @GetMapping("/rider-profile")
    public String riderProfile(){return "rider-profile";}
    @GetMapping("/book-ride")
    public String bookRide(){return "book-ride";}
}
