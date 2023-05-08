package com.example.dummySbrigit.Repo;

import com.example.dummySbrigit.Entities.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepo extends JpaRepository<Uuid,String> {
    int deleteByUuid(String uuid);
    Uuid getUuidRepoByUuid(String uuid);
}
