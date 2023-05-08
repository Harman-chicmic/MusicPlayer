package com.example.dummySbrigit.Services;

import com.example.dummySbrigit.Entities.Roles;
import com.example.dummySbrigit.Entities.User;
import com.example.dummySbrigit.Entities.UserRole;
import com.example.dummySbrigit.Repo.RoleRepo;
import com.example.dummySbrigit.Repo.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserRoleRepo userRoleRepo;
    public void saveUserRole(UserRole userRole){

        userRoleRepo.save(userRole);

    }
//    public int findRoleId(String role) {
//        // return 0;
//        return roleRepo.findRoleIdByRole(role);
//    }
//
//    public String findRole(int roleId) {
//        // return 0;
//        return roleRepo.findRole(roleId);
//    }
//    public int findRoleId(int userId) {
//        return userRoleRepo.findroleIdbyuserId(userId);
//    }
}
