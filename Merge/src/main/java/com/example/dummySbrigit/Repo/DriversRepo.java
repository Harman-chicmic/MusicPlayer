package com.example.dummySbrigit.Repo;

//import com.example.dummySbrigit.Entities.Drivers;
import com.example.dummySbrigit.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriversRepo extends JpaRepository<User,Long> {

    User getUserByEmail(String email);
    User findByEmail(String email);
    User getUserById(int id);
    @Override
    @Query("select e from User e where e.deleted = false")
    List<User> findAll();

    @Query("select e from User e where e.deleted = false")
    Page<User> findAllDrivers( Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "Update User us set us.firstName = :firstName, us.lastName=:lastName,us.email=:email," +
            " us.phone=:phone where us.email = :email")
    void updateColumnsInDrivers(
            @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email,
            @Param("phone") String phone);
    @Query("select d from User d where (d.firstName like concat('%',:query,'%') or d.email like concat('%',:query,'%')) and deleted =false")
    Page<User> searchDrivers(Pageable pageable,@Param("query")String query);

//    @Query("select driver from Drivers driver order by email limit 2 offset 4")
//    List<Drivers> getDriversList(Pageable pageable);
//    Page<Drivers> findByDeletedOrderByEmail();
    //void suspendDriver(int id,boolean status);

}
