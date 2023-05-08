package com.example.dummySbrigit.Repo;

//import com.example.dummySbrigit.Entities.Admin;

import com.example.dummySbrigit.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepo extends JpaRepository<User,Long> {
    User getUserByEmail(String email);

    @Transactional
    @Modifying
    @Query(value=
            "Update User us set us.firstName = :firstName, us.lastName=:lastName," +
                    " us.phone=:phone, us.country=:country, us.imgUrl=:imgUrl where us.email=:email"
    )
        //(
        //            value = "Update Users us Set us.firstName = :firstName, us.lastName = :lastName, us.phoneNo = :phoneNo Where us.email = :email"
        //    )
    void updateAdminInAdmin(
    @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email,
    @Param("phone") String phone, @Param("country") String country,@Param("imgUrl") String imgUrl);

}