package com.example.dummySbrigit.Controllers;

import com.example.dummySbrigit.Dto.EmailDto;
import com.example.dummySbrigit.Entities.PasswordResetToken;
import com.example.dummySbrigit.Entities.Roles;
import com.example.dummySbrigit.Entities.User;
import com.example.dummySbrigit.Entities.UserRole;
import com.example.dummySbrigit.Repo.PasswordResetTokenRepo;
import com.example.dummySbrigit.Repo.RoleRepo;
import com.example.dummySbrigit.Services.DriversService;
import com.example.dummySbrigit.Services.RoleService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.bcel.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class MevronControllers {
    //    private final UserService userService;
//
//    public UserControllers(UserService userService) {
//        this.userService = userService;
//    }
//
    @Autowired
    DriversService driversService;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PasswordResetTokenRepo passwordResetTokenRepo;
    @Autowired
    RoleService roleService;

    @GetMapping("/abcd")
    public String home(Model model) {
        model.addAttribute("index", "Mevron-Home");
        System.out.println("this is home");
        return "indexMevron";
    }

    //
    @GetMapping("/signup-driver")
    public String signupDriver(Model model) {
        model.addAttribute("index", "Mevron-Driver Signup");
        List<Roles> roles= roleRepo.getRoles();
        model.addAttribute("roles",roles);
        System.out.println("this is driver signup");
        return "signup-driver";
    }

    @GetMapping("/signup-rider")
    public String signupRider(Model model) {
        model.addAttribute("index", "Mevron-Rider Signup");
        System.out.println("rider signup");
        return "signup-rider";
    }

    @GetMapping("/driver-password")
    public String DriverPaswword(@RequestParam String email, HttpServletRequest request, Model model,HttpServletResponse response) {
        model.addAttribute("email", email);
        List<Roles> roles= roleRepo.getRoles();
        model.addAttribute("roles",roles);
        //System.out.println(role);
        System.out.println(roles);
        System.out.println(email);
        return "driver-password";
    }

    @GetMapping("/driver-login")
    public String driverLogin(Model model) {
        model.addAttribute("index", "Mevron-driver login");
        model.addAttribute("emailDto", new EmailDto());

        System.out.println("driver login");
        return "driver-login";
    }

    @GetMapping("/rider-login")
    public String riderLogin(Model model) {
        model.addAttribute("index", "Mevron-rider login");
        System.out.println("rider login");
        return "rider-login";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("index", "Mevron-about");
        System.out.println("about");
        return "about";
    }

    @GetMapping("/safety")
    public String safety(Model model) {
        model.addAttribute("index", "Mevron-safety");
        System.out.println("safety");
        return "safety";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("index", "Mevron-contact");
        System.out.println("contact");
        return "contact";
    }

    @PostMapping("/driver-login-attempt")
    @ResponseBody
    public User driverLogin(@RequestBody HashMap<String, String> mp,HttpServletRequest request, HttpServletResponse response) {
        String email = mp.get("email");
        User user1 = driversService.getUserByEmail(email);
        String role=request.getParameter("role");
//        Cookie cookie =new Cookie("selectedRole",role);
//        response.addCookie(cookie);
        System.out.println(role);
        System.out.println(user1);
        return user1;
    }

    @PostMapping("/register")
    public String Register(HttpServletRequest request) {
        String fName = request.getParameter("fname");
        String lName = request.getParameter("lname");
        String email = request.getParameter("email");
        String phNo = (request.getParameter("phone"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("cnfPassword");
        String city = request.getParameter("city");
        String inviteCode = request.getParameter("inviteCode");
        String role=request.getParameter("role");

//        if(!confirmPassword.equals(password)) {
//            System.out.println("//////////////////" + "inside");
//            return "error";
//        }
//    List<String> list= new ArrayList<>();
//    list.add("driver");
        User user1=driversService.getUserByEmail(email);

        if(user1==null||user1.getUserRoles()==null){
        User user = User.builder()
                .firstName(fName)
                .lastName(lName)
                .email(email)
                .phone(phNo)
                .password(password)
                .country(city)
                .build();
        System.out.println("////////////" + user);
        driversService.saveUser(user);
        Roles role1 = roleRepo.findByRoleName(role);
        UserRole userRole = new UserRole();
        userRole.setUserId(user);
        userRole.setRoleId(role1);
        roleService.saveUserRole(userRole);
        driversService.register(user);}
        else if(user1.getUserRoles()!=null){
            Roles role1 = roleRepo.findByRoleName(role);
            UserRole userRole = new UserRole();
            userRole.setUserId(user1);
            userRole.setRoleId(role1);
            roleService.saveUserRole(userRole);
        }
//    User user1 = driversService.getUserById(user.getId());
//    Roles roles = roleRepo.findByRoleName("Driver");
//    UserRole userRole = UserRole.builder().userId(user1.getId()).roleId(roles.getRId()).build();
//    List<UserRole> list=new ArrayList<>();
//    list.add(userRole);
//    user1.setUserRoles(list);
//    driversService.saveUser(user1);
//    driversService.addRoleToUser("Driver");
//    driversService
        return "driver-documentation";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/set-new-password")
    public String setPassword(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        User user = driversService.getUserByEmail(email);
        //String resetPasswordLink="http://localhost:8081/reset-email";
        driversService.createPasswordResetTokenForUser(user);
        return "reset-email";
    }

    @GetMapping("/EnterNewPassword/{token}/{email}")
    public String Enter(HttpServletRequest
                                request, @PathVariable("token") String token, @PathVariable("email") String email, Model model) {
        //String newPassword=request.getParameter("password");
        PasswordResetToken passwordResetRequest = passwordResetTokenRepo.findByToken(token);
//        User user= passwordResetRequest.getUser();
        if (passwordResetRequest == null || passwordResetRequest.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "redirect:/login?error=InvalidToken";
        }
        model.addAttribute("token", token);
        return "forgotPasswordForm";
    }
    @PostMapping("/EnterNewPassword/{token}/{email}")
    public String resetPassword(HttpServletRequest
                                request,@PathVariable("token") String token,@PathVariable("email") String email){
        PasswordResetToken passwordResetRequest = passwordResetTokenRepo.findByToken(token);
        User user = driversService.getUserByEmail(email);
        System.out.println(user);
        String newPassword=request.getParameter("password");
        if (passwordResetRequest == null || passwordResetRequest.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "redirect:/login?error=InvalidToken";
        }
        user.setPassword(newPassword);
        driversService.save(user);
        passwordResetTokenRepo.delete(passwordResetRequest);
        //return "redirect:/login?success=PasswordReset";
        return "driver-login";
    }
    @GetMapping("/selectRoles")
    public String selectRoles(HttpServletRequest request){
        return "rolesSelect";
    }


    @GetMapping("/role/list")
    @ResponseBody
    public List<Roles> getRoleList(){
        return roleRepo.getRoles();
    }
//    @PostMapping("/book-ride")
//    public String

}

