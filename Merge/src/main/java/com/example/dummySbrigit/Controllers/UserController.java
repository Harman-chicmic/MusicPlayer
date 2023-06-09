package com.example.dummySbrigit.Controllers;

//import com.example.dummySbrigit.Entities.Admin;
//import com.example.dummySbrigit.Entities.Drivers;
import com.example.dummySbrigit.Entities.Roles;
import com.example.dummySbrigit.Entities.User;
import com.example.dummySbrigit.Entities.UserRole;
import com.example.dummySbrigit.Entities.Uuid;
import com.example.dummySbrigit.Repo.RoleRepo;
import com.example.dummySbrigit.Services.AdminService;
import com.example.dummySbrigit.Services.DriversService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@CrossOrigin("https://c93e-112-196-113-2.ngrok-free.app")
@Controller
public class UserController {
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String imagePath= "/home/chicmic/Downloads/dummySbrigit/src/main/resources/static/assets/img/";
    @Autowired
    AdminService adminService;
    @Autowired
    DriversService driversService;
    @Autowired
    RoleRepo roleRepo;
    @GetMapping("/abc")
    public String home(Model model) {
        model.addAttribute("index", "Mevron-Home");
        System.out.println("this is home");
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
       // Uuid uuid = adminService.getToken()
        User admin = new User();
        System.out.println(ANSI_CYAN + admin.getFirstName()+"from dashboard");
        adminService.getToken("uuid");
        //adminService.getUserByEmail("email");
        model.addAttribute("FirstName",admin.getFirstName());
        return "dashboard";
    }
    @GetMapping("/MyProfile")
    public String MyProfile(){
        return "myProfile";
    }
    @PostMapping("/updateProfile")
    public String updateProfile(HttpServletRequest request,Model model, @RequestParam("imgUrl")MultipartFile file) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = (request.getParameter("phone"));
        String country = request.getParameter("country");
        if(file.isEmpty()){
        }
        else{
            String folder = imagePath;
            System.out.println(imagePath);
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder +  file.getOriginalFilename());
            System.out.println("\u001B[33m" + path + "\u001B[0m" );
            Files.write(path, bytes);
        }
        System.out.println("inside////////////" + firstName + lastName + email + phone);
        User admin= adminService.UpdateAdminInUser(firstName,lastName,email,phone,country,"/assets/img/"+file.getOriginalFilename());;
        System.out.println(admin);
        model.addAttribute("imgUrl","/assets/img/"+file.getOriginalFilename());
        return "myProfile";
    }

    @GetMapping("/addDriver")
    public String addDriver(){
        return "addDriver";
    }
//    @GetMapping("/drivers")
//    public String drivers(){
//        return "drivers";
//    }

    @GetMapping("/drivers/{page}")
    public String listObjects(@PathVariable Integer page, Model model, HttpServletRequest request) {
        //List<Drivers> drivers = driversService.allDrivers();
        //int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(page);
        if(page == null) page = 0;
        Pageable pageable=PageRequest.of(page,5);
         String sortBy = request.getParameter("sortDriversBy");
         if (sortBy==null){
             sortBy="firstName";
         }
        //System.out.println(drivers);
        //Page<Drivers> drivers=driversService.allDrivers(pageable);
        Page<User> drivers = driversService.getAllUsers(page,5,sortBy);
         List<User> drivers1= driversService.allDriver();
        for (User i:
             drivers1) {
            List<UserRole>userRoleList =drivers1.get(i.getId()-1).getUserRoles();
        }

         List<UserRole>userRoleList =drivers1.get(0).getUserRoles();

        //model.addAttribute("objects", drivers);
        model.addAttribute("objects",drivers);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",drivers.getTotalPages());
        model.addAttribute("sortBy", sortBy);

        List<Roles> roles= roleRepo.getRoles();
        model.addAttribute("roles",roles);

        return "drivers";
    }

//    @GetMapping("/drivers")
//    public String listObjects(Model model){
//        List<Drivers> drivers=driversService.allDrivers();
//        model.addAttribute("objects",drivers);
//        return "drivers";
//    }

    @GetMapping("/driver-overview")
    public String driverOverview(){
        return "driver-overview";
    }
    @GetMapping("/updateDriver")
    public String updateDriver(){
        return "updateDriver";
    }
    @GetMapping("/softDelete")
    public String softDelete(Model model,HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
        driversService.softDelete(id);
        Pageable pageable=PageRequest.of(0,5);
        //List<Drivers> drivers = driversService.allDriver();
        Page<User> drivers=driversService.allDrivers(pageable);
        System.out.println(drivers);
        model.addAttribute("objects", drivers);
        model.addAttribute("currentPage",0);
        model.addAttribute("totalPages",drivers.getTotalPages());
        String sortBy="firstName";
        model.addAttribute("sortBy", sortBy);
        return "drivers";
    }
    @GetMapping("/searchDrivers")
    public String searchDrivers(HttpServletRequest request,Model model){
        String query= request.getParameter("query");

        Pageable pageable=PageRequest.of(0,5);

        Page<User> drivers=driversService.searchDriversByFirstNameAndEmail(pageable,query);

        //List<Drivers> drivers = driversService.allDriver();

        System.out.println(drivers);
        model.addAttribute("objects", drivers);
        model.addAttribute("currentPage",0);
        model.addAttribute("totalPages",drivers.getTotalPages());
        String sortBy="firstName";
        model.addAttribute("sortBy", sortBy);
        return "drivers";
    }
    @GetMapping("/sortDrivers")
    public String sortDrivers(HttpServletRequest request,Model model){
        String sortBy=request.getParameter("sortDriversBy");
        Page<User> sortedDrivers = driversService.getAllUsers(0,5,sortBy);
        model.addAttribute("objects", sortedDrivers);
        model.addAttribute("currentPage",0);
        model.addAttribute("totalPages",sortedDrivers.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        return "redirect:/drivers/0?sortDriversBy="+sortBy;
    }
    @GetMapping("/suspendDrivers")
    public String suspendDrivers(Model model,HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        User driver=driversService.getUserById(id);
        Boolean status1=driver.isStatus();


        //boolean status;
        if (status1){
            status1 = false ;
        }
        else{
            status1 = true ;
        }
        driversService.suspendDrivers(id,status1,driver);

        Pageable pageable=PageRequest.of(0,5);

        Page<User> drivers=driversService.searchDriversByFirstNameAndEmail(pageable,
                "");


        model.addAttribute("objects", drivers);

        model.addAttribute("currentPage",0);
        model.addAttribute("totalPages",drivers.getTotalPages());
        String sortBy="";
        model.addAttribute("sortBy", sortBy);
        return "drivers";
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
        User user = User.builder()
                .firstName(fName)
                .lastName(lName)
                .email(email)
                .phone(phNo)
                .password(password)
                //.city(city)
                .build();
        System.out.println("////////////" + user);
        driversService.saveUser(user);
        driversService.register(user);
        return "driverOtp";
    }
    @GetMapping("/verify")
    public String verifyUser(@RequestParam String email, @RequestParam int otp, HttpServletRequest request) {
//        boolean isVerified = driversService.verify(email, otp);
//        System.out.println("verification");
//        if (isVerified) {
//            return "OTP verification successful";
//        } else {
//            return "Invalid OTP";
//        }
        request.getParameter("email");
        request.getParameter("otp");
        boolean isVerified = driversService.verify(email, otp);
        System.out.println("verification");
        if (isVerified) {
            return "driverLogin";
        } else {
            return "Invalid OTP";
        }
    }
    @GetMapping("/driverLogin")
    public String driverLogin(HttpServletRequest request){
        request.getParameter("username");
        request.getParameter("password");
        return "index";
    }
}
