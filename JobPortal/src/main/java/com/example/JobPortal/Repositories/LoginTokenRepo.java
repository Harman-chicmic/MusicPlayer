package com.example.JobPortal.Repositories;

import com.example.JobPortal.Entities.LoginToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginTokenRepo extends JpaRepository<LoginToken,String> {
    int deleteByloginToken(String loginToken);
    LoginToken getLoginTokenRepoByLoginToken(String loginToken);
}