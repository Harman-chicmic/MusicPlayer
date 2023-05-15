package com.example.dummySbrigit.Controllers;

import com.example.dummySbrigit.Entities.Locations;
import com.example.dummySbrigit.Entities.RideDetails;
import com.example.dummySbrigit.Entities.User;
import com.example.dummySbrigit.Repo.LocationsRepo;
import com.example.dummySbrigit.Repo.PickupDropoffRepo;
import com.example.dummySbrigit.Repo.RideDetailsRepo;
import com.example.dummySbrigit.Services.DriversService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Controller
public class PageControllers {
    @Autowired
    LocationsRepo locationsRepo;
    @Autowired
    PickupDropoffRepo pickupDropoffRepo;
    @Autowired
    DriversService driversService;
//    @Autowired
//    RideDetailsRepo rideDetailsRepo;
    @Value("${stripe.public.key}")
    private String stripePublicKey;
    @GetMapping("/driver-profile")
    public String driverProfile(){
        return "driver-profile";
    }
    @GetMapping("/rider-payment")
    public String riderPayment(){return "rider-payment";}
    @GetMapping("/rider-profile")
    public String riderProfile(){return "rider-profile";}
    @GetMapping("/book-ride")
    public String bookRide(Model model, HttpServletRequest request){
        List<Locations> location=locationsRepo.getLocations();
        model.addAttribute("location",location);
        String pickup= request.getParameter("pickup");
        String dropoff= request.getParameter("dropoff");
        System.out.println(pickup);
        return "book-ride";
    }
    @GetMapping("/checkout")
    public String abcd(Model model){
        model.addAttribute("stripePublicKey",stripePublicKey);
        System.out.println(stripePublicKey);
        return "checkout";}
    @GetMapping("/map")
    public String maap(){return "map";}
    @GetMapping("/pick-drop")
    public String pickDrop(HttpServletRequest request, Model model,  Principal principal){
        String pickup= request.getParameter("pickup");
        String dropoff= request.getParameter("dropoff");
        System.out.println(pickup);
        System.out.println("\u001B[31m"+dropoff+"\u001B[0m");
        Integer distance = pickupDropoffRepo.findDistance(pickup,dropoff);
        model.addAttribute("distance",distance);
        System.out.println("\u001B[31m"+distance+"\u001B[0m");
        RideDetails rideDetails = new RideDetails();
        rideDetails.setPickUp(pickup);
        rideDetails.setDropOff(dropoff);
        rideDetails.setAmount(distance*10+50);
        User user=driversService.getUserByEmail(principal.getName());
        rideDetails.setUser(user);

        return "pick-drop";
    }
}





