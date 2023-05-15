package com.example.JobPortal.Repositories;

import com.example.JobPortal.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users,String> {
    public Users findUsersByEmail(String email);
    public Users findUserById(int id);
}
