package com.example.JobPortal.Controllers;

import com.example.JobPortal.Entities.Users;
import com.example.JobPortal.Services.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
public class UserControllers {
    private Logger logger= LoggerFactory.getLogger(UserControllers.class);
    public static final String imagePath= "/home/chicmic/Downloads/JobPortal/src/main/resources/static/assets/img/";
    public static final String resumePath = "/home/chicmic/Downloads/JobPortal/src/main/resources/static/assets/files/";
    @Autowired
    UsersService usersService;
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(HttpServletRequest request,@RequestParam("imgUrl") MultipartFile imgFile,
                           @RequestParam("resumeUrl") MultipartFile resumeFile) throws IOException {
        String userToken=UUID.randomUUID().toString();
        String fullName=request.getParameter("fullName");
        String email= request.getParameter("email");
        String phone=request.getParameter("phone");
        String password=request.getParameter("password");
        String bio=request.getParameter("bio");
//        List<String> skills= List.of(request.getParameterValues("skills"));
        String imgFolder = imagePath;
        String resumeFolder=resumePath;
        System.out.println(imagePath);
        byte[] imgFileBytes = imgFile.getBytes();
        byte[] resumeFileBytes= resumeFile.getBytes();
        Path imgPath = Paths.get(imgFolder +  imgFile.getOriginalFilename());
        Path resumePath=Paths.get(resumeFolder+resumeFile.getOriginalFilename());
        logger.info(imgPath.toString()+resumePath.toString());
        Files.write(imgPath, imgFileBytes);
        Files.write(resumePath,resumeFileBytes);
        Users user= Users.builder()
                .fullName(fullName)
                .email(email)
                .phone(phone)
                .password(password)
                .userToken(userToken)
                .Bio(bio)
                .imgUrl("/assets/img"+imgFile.getOriginalFilename())
                .ResumeUrl("/assets/files"+resumeFile.getOriginalFilename())
                .build();
        usersService.saveUser(user);
        System.out.println("lalalalalala");

        return "";
    }


}
