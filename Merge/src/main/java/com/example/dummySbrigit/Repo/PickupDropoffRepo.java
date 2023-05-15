package com.example.dummySbrigit.Repo;

import com.example.dummySbrigit.Entities.PickupDropoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PickupDropoffRepo extends JpaRepository<PickupDropoff,String> {
    @Query(value = "SELECT distance from PickupDropoff pd where pd.pickupLocation=:pickupLocation and pd.dropoffLocation=:dropoffLocation")
    Integer findDistance(String pickupLocation, String dropoffLocation);
}
