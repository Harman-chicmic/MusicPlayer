package com.example.dummySbrigit.Repo;

import com.example.dummySbrigit.Entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepo extends JpaRepository<Roles,Integer> {
//    void saveRoleById(int id);
    Roles findByRoleName(String roleName);
    @Query(value = "select * from roles",nativeQuery = true)
    List<Roles> getRoles();

}
