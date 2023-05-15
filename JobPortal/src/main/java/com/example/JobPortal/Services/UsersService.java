package com.example.JobPortal.Services;

import com.example.JobPortal.Entities.LoginToken;
import com.example.JobPortal.Entities.Users;
import com.example.JobPortal.Repositories.LoginTokenRepo;
import com.example.JobPortal.Repositories.UsersRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UsersService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    LoginTokenRepo loginTokenRepo;

    public Users getUserByEmail(String email) {
        return usersRepo.findUsersByEmail(email);
    }

    public void saveUser(Users user) {
        usersRepo.save(user);
    }

    public Users getUser(String email) {
        return usersRepo.findUsersByEmail(email);
    }

    public LoginToken CreateToken(LoginToken loginToken) {
        System.out.println(loginToken);
        if (loginTokenRepo == null) System.out.println("null");
        return loginTokenRepo.save(loginToken);
    }
    public Users getUserById(int id){return usersRepo.findUserById(id);}


    public LoginToken getToken(String loginToken) {
        return loginTokenRepo.getLoginTokenRepoByLoginToken(loginToken);}
    public Users register(Users user) {
        // Generate OTP
        int otp = new Random().nextInt(999999);
        System.out.println(otp);

        // Send OTP to user's email
        String subject = "OTP for user registration";
        String message = "Your OTP is: " + otp;
        user.setOtp(otp);
        usersRepo.save(user);
        sendEmailForOtp(user.getEmail(), subject, message);

        // Save user to database
        // ...

        return user;
    }
//    @Async("threadPoolTaskExecutor")
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
        Users user = usersRepo.findUsersByEmail(email);
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

    @Transactional
    public int deleteToken (String loginToken){
            return loginTokenRepo.deleteByloginToken(loginToken);
    }
}
