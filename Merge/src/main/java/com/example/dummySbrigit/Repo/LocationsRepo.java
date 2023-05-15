package com.example.dummySbrigit.Repo;

import com.example.dummySbrigit.Entities.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationsRepo extends JpaRepository<Locations,String> {
    @Query(value = "select * from locations", nativeQuery = true)
    List<Locations> getLocations();
}
