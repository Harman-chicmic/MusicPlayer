package com.example.dummySbrigit.Services;

//import com.example.dummySbrigit.Entities.Admin;
//import com.example.dummySbrigit.Entities.User;
import com.example.dummySbrigit.Entities.User;
import com.example.dummySbrigit.Entities.Uuid;
import com.example.dummySbrigit.Repo.AdminRepo;
//import com.example.dummySbrigit.Repo.UserRepo;
import com.example.dummySbrigit.Repo.UuidRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    UuidRepo uuidRepo;

    public User getUserByEmail(String email) {
        return adminRepo.getUserByEmail(email);
    }

    public void saveAdmin(User user) {
        adminRepo.save(user);
    }

    public User getAdmin(String email) {
        return adminRepo.getUserByEmail(email);
    }
    public Uuid CreateToken(Uuid uuid){
        System.out.println(uuid);
        if (uuidRepo==null) System.out.println("nulll");
        // return uuidEntity;

        return uuidRepo.save(uuid);

    }
    public User UpdateAdminInUser(String firstName, String lastName, String email, String phone, String country, String imgUrl ){
        System.out.println(firstName+lastName);
        adminRepo.updateAdminInAdmin(firstName,lastName,email,phone,country,imgUrl);
        return adminRepo.getUserByEmail(email);
    }
//        public void UpdateAdminInUser(Admin admin, long id){
//            Admin admin1 = adminRepo.fi //Consider em as JPA EntityManager
//            customer.setName(customerDto.getName);
//            em.merge(customer);
//
//    }

    public Uuid getToken(String uuid){
        return uuidRepo.getUuidRepoByUuid(uuid);
    }

    @Transactional
    public int deleteToken(String uuid) {
        return  uuidRepo.deleteByUuid(uuid);
    }

}
