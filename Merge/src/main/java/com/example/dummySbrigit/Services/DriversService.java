package com.example.dummySbrigit.Services;

//import com.example.dummySbrigit.Entities.Admin;
//import com.example.dummySbrigit.Entities.Drivers;
import com.example.dummySbrigit.Entities.PasswordResetToken;
import com.example.dummySbrigit.Entities.Roles;
import com.example.dummySbrigit.Entities.User;
import com.example.dummySbrigit.Entities.UserRole;
import com.example.dummySbrigit.Repo.DriversRepo;
import com.example.dummySbrigit.Repo.PasswordResetTokenRepo;
import com.example.dummySbrigit.Repo.RoleRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service

public class DriversService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    DriversRepo driversRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PasswordResetTokenRepo passwordResetTokenRepo;
    public void save(User user){driversRepo.save(user);}
    public User getUserById(int id){return driversRepo.getUserById(id);}

    public User getUserByEmail(String email) {
        return driversRepo.getUserByEmail(email);
    }

    public void saveUser(User driver){
        driversRepo.save(driver);
    }
    public User getUser(String email){
        return driversRepo.getUserByEmail(email);
    }
    public List<User> allDriver(){
        return driversRepo.findAll();
    }
    public Page<User> allDrivers( Pageable pageable){
        return driversRepo.findAllDrivers(pageable);
    }
    public User UpdateDriversInUser(String firstName, String lastName, String email, String phone ){
        System.out.println(firstName+lastName);
        driversRepo.updateColumnsInDrivers(firstName,lastName,email,phone);
        System.out.println(email);
        return driversRepo.getUserByEmail(email);
    }
    public void updateDrivers(String firstName, String lastName, String email, String phone){
        driversRepo.updateColumnsInDrivers(firstName,lastName,email,phone);
    }
    public void softDelete(int id) {
        Optional<User> optionalEntity = driversRepo.findById((long)id);
        if (optionalEntity.isPresent()) {
            User entity = optionalEntity.get();
            entity.setDeleted(true);
            driversRepo.save(entity);
        }
    }
    public Page<User>searchDriversByFirstNameAndEmail(Pageable pageable, @Param("query")String query){
        return driversRepo.searchDrivers(pageable,query);
    }
    public  Page<User> getAllUsers(Integer pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, sortBy);

        String target="";

        Page<User>  usersList = driversRepo.searchDrivers(pageable,target);




        return usersList ;
    }
    public void suspendDrivers(int id, boolean status, User driver){

        driver.setStatus(status);
        driversRepo.save(driver);
    }

    public User register(User user) {
        // Generate OTP
        int otp = new Random().nextInt(999999);
        System.out.println(otp);

        // Send OTP to user's email
        String subject = "OTP for user registration";
        String message = "Your OTP is: " + otp;
        user.setOtp(otp);
        driversRepo.save(user);
        sendEmailForOtp(user.getEmail(), subject, message);

        // Save user to database
        // ...

        return user;
    }
    @Async("threadPoolTaskExecutor")
    private void sendEmailForOtp(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("harmanjyot.singh@chicmic.co.in");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
    public boolean verify(String email, int otp) {
        // Get user by email
        User user = driversRepo.findByEmail(email);
        if (user == null) {
            return false;
        }

        // Check if OTP is correct
        if (user.getOtp() == otp) {
            // Update user's OTP status to verified
            user.setOtpVerified(true);
            //userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
    public Roles createRole(Roles roles){
        return roleRepo.save(roles);
    }

    public void addRoleToUser( String roleName) {


        Roles role = roleRepo.findByRoleName(roleName);

    }
//    public void addRoleToUser(int id,String roleName){
//       // roleRepo.saveRoleById(id);
//    }
public void sendEmailForPassword(String recipientEmail, String link)
        throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom("contact@shopme.com", "Shopme Support");
    helper.setTo(recipientEmail);

    String subject = "Here's the link to reset your password";

    String content = "<p>Hello,</p>"
            + "<p>You have requested to reset your password.</p>"
            + "<p>Click the link below to change your password:</p>"
            + "<p style=\"color:blue;\"><a href=\"" + link + "\">Change my password</a></p>"
            + "<br>"
            + "<p>Ignore this email if you do remember your password, "
            + "or you have not made the request.</p>";

    helper.setSubject(subject);

    helper.setText(content, true);

    javaMailSender.send(message);
    System.out.println("email sent");

    }
    public void createPasswordResetTokenForUser(User user) throws MessagingException, UnsupportedEncodingException {
        PasswordResetToken passwordResetRequest = new PasswordResetToken();
        UUID token1= UUID.randomUUID();
        passwordResetRequest.setUser(user);
        passwordResetRequest.setToken(token1.toString());
        passwordResetRequest.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        passwordResetTokenRepo.save(passwordResetRequest);
        String link1="http://localhost:8081/EnterNewPassword/"+ token1+"/"+user.getEmail();
        sendEmailForPassword(user.getEmail(), link1);
    }
    public void changeUserPassword(User user, String password) {
        user.setPassword(password);
        driversRepo.save(user);
    }


}
