package com.example.dummySbrigit.Repo;

import com.example.dummySbrigit.Entities.Drivers;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriversRepo extends JpaRepository<Drivers,Long> {

    Drivers getUserByEmail(String email);
    Drivers getUserById(int id);
    @Override
    @Query("select e from Drivers e where e.deleted = false")
    List<Drivers> findAll();

    @Query("select e from Drivers e where e.deleted = false")
    Page<Drivers> findAllDrivers( Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "Update Drivers us set us.firstName = :firstName, us.lastName=:lastName,us.email=:email," +
            " us.phone=:phone where us.email = :email")
    void updateColumnsInDrivers(
            @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email,
            @Param("phone") String phone);
    @Query("select d from Drivers d where (d.firstName like concat('%',:query,'%') or d.email like concat('%',:query,'%')) and deleted =false")
    Page<Drivers> searchDrivers(Pageable pageable,@Param("query")String query);

//    @Query("select driver from Drivers driver order by email limit 2 offset 4")
//    List<Drivers> getDriversList(Pageable pageable);
//    Page<Drivers> findByDeletedOrderByEmail();
    //void suspendDriver(int id,boolean status);

}
