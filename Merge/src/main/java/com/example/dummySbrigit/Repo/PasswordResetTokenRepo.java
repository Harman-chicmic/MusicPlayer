package com.example.dummySbrigit.Repo;

import com.example.dummySbrigit.Entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken,Integer> {
    public PasswordResetToken findByToken(String token);
}
