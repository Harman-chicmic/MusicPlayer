package com.example.dummySbrigit.Repo;

import com.example.dummySbrigit.Entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepo extends JpaRepository<UserRole,Integer> {
//    @Query("select roleId.roleName from UserRole userRole inner join userRole.roleId roleId where userRole.userId.uid = :userId")
//    List<String> findUserRoles(int  userId);
}
