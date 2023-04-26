package com.example.dummySbrigit.Services;

import com.example.dummySbrigit.Entities.Admin;
import com.example.dummySbrigit.Entities.Drivers;
import com.example.dummySbrigit.Repo.DriversRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service

public class DriversService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    DriversRepo driversRepo;
    public Drivers getUserById(int id){return driversRepo.getUserById(id);}

    public Drivers getUserByEmail(String email) {
        return driversRepo.getUserByEmail(email);
    }

    public void saveUser(Drivers driver){
        driversRepo.save(driver);
    }
    public Drivers getUser(String email){
        return driversRepo.getUserByEmail(email);
    }
    public List<Drivers> allDriver(){
        return driversRepo.findAll();
    }
    public Page<Drivers> allDrivers( Pageable pageable){
        return driversRepo.findAllDrivers(pageable);
    }
    public Drivers UpdateDriversInUser(String firstName, String lastName, String email, String phone ){
        System.out.println(firstName+lastName);
        driversRepo.updateColumnsInDrivers(firstName,lastName,email,phone);
        System.out.println(email);
        return driversRepo.getUserByEmail(email);
    }
    public void updateDrivers(String firstName, String lastName, String email, String phone){
        driversRepo.updateColumnsInDrivers(firstName,lastName,email,phone);
    }
    public void softDelete(int id) {
        Optional<Drivers> optionalEntity = driversRepo.findById((long)id);
        if (optionalEntity.isPresent()) {
            Drivers entity = optionalEntity.get();
            entity.setDeleted(true);
            driversRepo.save(entity);
        }
    }
    public Page<Drivers>searchDriversByFirstNameAndEmail(Pageable pageable, @Param("query")String query){
        return driversRepo.searchDrivers(pageable,query);
    }
    public  Page<Drivers> getAllUsers(Integer pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, sortBy);

        String target="";

        Page<Drivers>  usersList = driversRepo.searchDrivers(pageable,target);




        return usersList ;
    }
    public void suspendDrivers(int id, boolean status, Drivers driver){

        driver.setStatus(status);
        driversRepo.save(driver);
    }

    public Drivers register(Drivers user) {
        // Generate OTP
        int otp = new Random().nextInt(999999);
        System.out.println(otp);

        // Send OTP to user's email
        String subject = "OTP for user registration";
        String message = "Your OTP is: " + otp;
        user.setOtp(otp);
        driversRepo.save(user);
        sendEmail(user.getEmail(), subject, message);

        // Save user to database
        // ...

        return user;
    }
    @Async("threadPoolTaskExecutor")
    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("harmanjyot.singh@chicmic.co.in");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
    public boolean verify(String email, int otp) {
        // Get user by email
        Drivers user = driversRepo.findByEmail(email);
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
}
