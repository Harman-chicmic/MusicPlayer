package com.example.JobPortal.Repositories;

import com.example.JobPortal.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company,String> {
}
